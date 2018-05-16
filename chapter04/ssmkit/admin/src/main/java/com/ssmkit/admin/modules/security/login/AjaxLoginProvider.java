package com.ssmkit.admin.modules.security.login;

import com.ssmkit.admin.modules.security.jwt.JwtUserDetails;
import com.ssmkit.admin.modules.system.entity.Role;
import com.ssmkit.admin.modules.system.entity.User;
import com.ssmkit.admin.modules.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AjaxLoginProvider implements AuthenticationProvider {

    private final BCryptPasswordEncoder encoder;

    private final UserService userService;

    @Autowired
    public AjaxLoginProvider(final UserService userService, final BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "验证信息不存在");

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User user = userService.getByUsername(username);
        if(user == null || StringUtils.isBlank(user.getUsername())) {
            throw new UsernameNotFoundException("该用户不存在: " + username);
        }

        if (!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("验证失败,用户名或密码不合法");
        }

        List<Role> roleList = user.getRoles();

        if (roleList == null) throw new InsufficientAuthenticationException("该用户没有角色");

        List<GrantedAuthority> authorities = roleList.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRoleType()))
                .collect(Collectors.toList());

        JwtUserDetails jWTUserDetails = JwtUserDetails.create(user.getUsername(), authorities);

        return new UsernamePasswordAuthenticationToken(jWTUserDetails, null, jWTUserDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass));
    }
}
