package com.ssmkit.admin.modules.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ssmkit.security.jwt")
public class JwtSettings {
    /**
     * 设置token失效时间 默认为10min
     */
    private Integer tokenExpirationTime =  10;

    /**
     * Token 发行者.
     */
    private String tokenIssuer;

    /**
     * 用来做签名的key
     */
    private String tokenSigningKey;

    /**
     * refresh token 失效时长 默认为60min.
     */
    private Integer refreshTokenExpTime = 60;

    public Integer getTokenExpirationTime() {
        return tokenExpirationTime;
    }

    public void setTokenExpirationTime(Integer tokenExpirationTime) {
        this.tokenExpirationTime = tokenExpirationTime;
    }

    public String getTokenIssuer() {
        return tokenIssuer;
    }

    public void setTokenIssuer(String tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }

    public String getTokenSigningKey() {
        return tokenSigningKey;
    }

    public void setTokenSigningKey(String tokenSigningKey) {
        this.tokenSigningKey = tokenSigningKey;
    }

    public Integer getRefreshTokenExpTime() {
        return refreshTokenExpTime;
    }

    public void setRefreshTokenExpTime(Integer refreshTokenExpTime) {
        this.refreshTokenExpTime = refreshTokenExpTime;
    }
}
