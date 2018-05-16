package com.ssmkit.common.bean;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 *  统一返回结果类
 *  @author 曹亚普
 *  @version 2017/05/15
 */
public class BaseResult {

    /**
     * 状态码: 1成功，其他为失败
     */
    public int code;

    /**
     * HTTP状态码
     */
    public HttpStatus status;

    /**
     *  信息
     */
    public String message;

    /**
     * 时间戳
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    public Date timestamp;

    /**
     * 数据结果集
     */
    public Object data;

    public BaseResult(int code, HttpStatus status, String message, Date timestamp, Object data) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
