package com.ssmkit.admin.modules.system.entity;

import com.ssmkit.common.entity.BaseEntity;

import javax.persistence.Table;

/**
 * 角色权限Entity
 * @author 曹亚普
 *
 * @version 2018/05/10
 */
@Table(name = "system_role")
public class Role extends BaseEntity {

	private static final long serialVersionUID = 917365174244457590L;
	
	/**
	 * 角色名称
	 */
	private String name;
	
	/**
	 * 角色类型
	 */
	private String roleType;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	

}
