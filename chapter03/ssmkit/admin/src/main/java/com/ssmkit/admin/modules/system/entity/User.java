package com.ssmkit.admin.modules.system.entity;

import com.ssmkit.common.entity.BaseEntity;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 用户Entity
 * 
 * @author 曹亚普
 *
 * @version 2018/05/10
 */
@Table(name = "system_user")
public class User extends BaseEntity {

	private static final long serialVersionUID = 7829209268478894182L;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 角色
	 */
	@Transient
	private List<Role> roles;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
