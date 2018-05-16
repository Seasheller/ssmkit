package com.ssmkit.admin.modules.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  JwtAuthenticationEntryPoint
 *  @author 曹亚普
 *  @version  2018/05/10
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
            throws IOException, ServletException {
        //当未认证授权的用户调用REST接口时，向前台发送401错误
        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
    }
}
