package com.ssmkit.admin.modules.system.service;

import com.ssmkit.admin.modules.system.entity.Role;

import java.util.List;

/**
 * 角色 Service
 * 
 * @author 曹亚普
 * 
 * @version 2018/05/10
 *
 */
public interface RoleService {

	List<Role> findByUsername(String username);
}
