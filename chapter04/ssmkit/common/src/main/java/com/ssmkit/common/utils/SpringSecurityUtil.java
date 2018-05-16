package com.ssmkit.common.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 全局工具类 用来获取当前用户信息
 * 
 * @author 曹亚普
 * @version 2018-05-09
 */
public class SpringSecurityUtil {

	/**
	 * 获取当前用户的唯一标识
	 * 
	 * @return UserDetails
	 */
	public static UserDetails getPrincipal() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails;
	}

	/**
	 * 获取当前用户的用户名
	 * 
	 * @return String username
	 */
	public static String getCurrentUsername() {
		return getPrincipal().getUsername();
	}
}
