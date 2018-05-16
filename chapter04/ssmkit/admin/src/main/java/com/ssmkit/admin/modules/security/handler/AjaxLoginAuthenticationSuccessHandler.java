package com.ssmkit.admin.modules.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssmkit.admin.modules.security.jwt.JwtUserDetails;
import com.ssmkit.admin.modules.security.jwt.token.Token;
import com.ssmkit.admin.modules.security.jwt.token.TokenFactory;
import com.ssmkit.common.bean.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录成功 Handler 
 * 
 * @author 曹亚普
 *
 * @version 2017/05/15
 */
@Component
public class AjaxLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private ObjectMapper mapper;
    private TokenFactory tokenFactory;

    @Autowired
    public AjaxLoginAuthenticationSuccessHandler(ObjectMapper mapper, TokenFactory tokenFactory) {
        this.mapper = mapper;
        this.tokenFactory = tokenFactory;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
        
        Token accessToken = tokenFactory.createAccessJwtToken(jwtUserDetails);
        //Token refreshToken = tokenFactory.createRefreshToken(jwtUserDetails);
        
        Map<String, String> tokenMap = new HashMap<String, String>();
        tokenMap.put("token", accessToken.getToken());
        //tokenMap.put("refreshToken", refreshToken.getToken());

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getWriter(), new BaseResult(1, HttpStatus.OK, "登陆成功", new Date(), tokenMap));

        clearAuthenticationAttributes(request);
    }

    /**
     * Removes temporary authentication-related data which may have been stored
     * in the session during the authentication process..
     * 
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
