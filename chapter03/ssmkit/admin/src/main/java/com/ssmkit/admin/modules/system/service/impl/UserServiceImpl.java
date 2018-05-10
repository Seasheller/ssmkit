package com.ssmkit.admin.modules.system.service.impl;

import com.ssmkit.admin.modules.system.dao.UserDao;
import com.ssmkit.admin.modules.system.entity.User;
import com.ssmkit.admin.modules.system.service.UserService;
import com.ssmkit.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
	private static final long serialVersionUID = 7558570979368623590L;
	
	@Autowired
	private UserDao userDao;

	@Override
	public User getByUsername(String username) {
		return userDao.findByUsername(username);
	}

}
