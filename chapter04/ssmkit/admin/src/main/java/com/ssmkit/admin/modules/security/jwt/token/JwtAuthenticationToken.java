package com.ssmkit.admin.modules.security.jwt.token;

import com.ssmkit.admin.modules.security.jwt.JwtUserDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 
 * @author 曹亚普
 * 
 * @version 2017/12/27
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 2917823334763231099L;
	private JwtToken jwtToken;
    private JwtUserDetails jwtUserDetails;

    public JwtAuthenticationToken(JwtToken unsafeToken) {
        super(null);
        this.jwtToken = unsafeToken;
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(JwtUserDetails jwtUserDetails, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.eraseCredentials();
        this.jwtUserDetails = jwtUserDetails;
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return jwtToken;
    }

    @Override
    public Object getPrincipal() {
        return this.jwtUserDetails;
    }

    @Override
    public void eraseCredentials() {        
        super.eraseCredentials();
        this.jwtToken = null;
    }
}
