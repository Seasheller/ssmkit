package com.ssmkit.admin.modules.system.service.impl;

import com.ssmkit.admin.modules.system.dao.RoleDao;
import com.ssmkit.admin.modules.system.entity.Role;
import com.ssmkit.admin.modules.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	public List<Role> findByUsername(String username) {
		
		return roleDao.findByUsername(username);
	}
	
}
