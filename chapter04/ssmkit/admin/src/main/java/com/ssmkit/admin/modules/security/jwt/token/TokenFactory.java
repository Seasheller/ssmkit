package com.ssmkit.admin.modules.security.jwt.token;

import com.ssmkit.admin.modules.security.config.JwtSettings;
import com.ssmkit.admin.modules.security.jwt.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenFactory {

    @Autowired
    private JwtSettings settings;

    /**
     * 创建新的token令牌
     *
     * @param jwtUserDetails
     *
     * @return AccessJwtToken
     */
    public JwtToken createAccessJwtToken(JwtUserDetails jwtUserDetails) {
        if (StringUtils.isBlank(jwtUserDetails.getUsername()))
            throw new IllegalArgumentException("用户名为空，无法创建token");

        if (jwtUserDetails.getAuthorities() == null || jwtUserDetails.getAuthorities().isEmpty())
            throw new IllegalArgumentException("用户没有任何权限信息，无法创建token");

        Claims claims = Jwts.claims().setSubject(jwtUserDetails.getUsername());
        claims.put("scopes", jwtUserDetails.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));

        LocalDateTime currentTime = LocalDateTime.now();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(settings.getTokenIssuer())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(settings.getTokenExpirationTime())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
                .compact();

        return new JwtToken(token, claims);
    }
}
