package com.ssmkit.admin.modules.security.exception;

import com.ssmkit.admin.modules.security.jwt.token.JwtToken;
import com.ssmkit.admin.modules.security.jwt.token.Token;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.core.AuthenticationException;

public class JwtExpiredTokenException extends AuthenticationException {

    private static final long serialVersionUID = -4382653752196203661L;

    private Token token;

    public JwtExpiredTokenException(String msg) {
        super(msg);
    }

    public JwtExpiredTokenException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtExpiredTokenException(JwtToken jwtToken, String msg, Throwable t) {
        super(msg, t);
        this.token = jwtToken;
    }
}
