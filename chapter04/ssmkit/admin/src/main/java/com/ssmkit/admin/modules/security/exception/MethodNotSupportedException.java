package com.ssmkit.admin.modules.security.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * 请求方法不支持 exception
 * 
 * @author 曹亚普
 *
 * @version 2018/05/15
 * 
 */
public class MethodNotSupportedException extends AuthenticationServiceException {

    private static final long serialVersionUID = 5385215721145902948L;

    public MethodNotSupportedException(String msg) {
        super(msg);
    }
}
