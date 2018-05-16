package com.ssmkit.admin.modules.security.jwt.token;

import com.ssmkit.admin.modules.security.exception.JwtExpiredTokenException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

public class JwtToken implements Token {
    private static Logger logger = LoggerFactory.getLogger(JwtToken.class);

    private String token;

    private Claims claims;

    public JwtToken(String token, Claims claims) {
        this.token = token;
        this.claims = claims;
    }

    @Override
    public String getToken() {
        return token;
    }

    public Claims getClaims() {
        return claims;
    }

    /**
     * 解析验证Token令牌.
     *
     * @throws BadCredentialsException
     * @throws JwtExpiredTokenException
     *
     */
    public Jws<Claims> parseClaims(String signingKey) {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(this.token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            logger.error("无效的令牌信息: ", ex);
            throw new BadCredentialsException("无效的令牌信息: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            logger.info("过期的令牌信息: ", expiredEx);
            throw new JwtExpiredTokenException(this, "过期的令牌信息: ", expiredEx);
        }
    }
}
