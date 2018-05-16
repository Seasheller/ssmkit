package com.ssmkit.admin.modules.system.dao;


import com.ssmkit.admin.modules.system.entity.Role;
import com.ssmkit.common.dao.BaseDao;

import java.util.List;

/**
 * 角色权限Mapper
 * @author 曹亚普
 *
 * @version 2018/05/10
 */
public interface RoleDao extends BaseDao<Role> {
	
	List<Role> findByUsername(String username);
	
}