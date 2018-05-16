package com.ssmkit.admin.modules.system.service;

import com.ssmkit.admin.modules.system.entity.User;
import com.ssmkit.common.service.BaseService;

/**
 * 角色 Service
 * 
 * @author 曹亚普
 * 
 * @version 2018/05/10
 *
 */
public interface UserService extends BaseService<User> {

	User getByUsername(String username);
	
}
