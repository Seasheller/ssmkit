package com.ssmkit.admin.modules.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssmkit.admin.modules.security.exception.JwtExpiredTokenException;
import com.ssmkit.admin.modules.security.exception.MethodNotSupportedException;
import com.ssmkit.common.bean.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 登录失败 Handler 
 * 
 * @author 曹亚普
 *
 * @version 2017/05/15
 */
@Component
public class AjaxAwareAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper mapper;
    
    @Autowired
    public AjaxAwareAuthenticationFailureHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }	
    
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
		
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		if (e instanceof BadCredentialsException) {
			mapper.writeValue(response.getWriter(), new BaseResult(0, HttpStatus.UNAUTHORIZED, "用户名或密码错误", new Date(),null));
		} else if (e instanceof JwtExpiredTokenException) {
			mapper.writeValue(response.getWriter(), new BaseResult(0, HttpStatus.UNAUTHORIZED, "令牌信息过期", new Date(),null));
		} else if (e instanceof MethodNotSupportedException) {
			mapper.writeValue(response.getWriter(), new BaseResult(0, HttpStatus.UNAUTHORIZED, e.getMessage(), new Date(),null));
		}

		mapper.writeValue(response.getWriter(), new BaseResult(0, HttpStatus.UNAUTHORIZED, "认证失败", new Date(),null));
	}
}
