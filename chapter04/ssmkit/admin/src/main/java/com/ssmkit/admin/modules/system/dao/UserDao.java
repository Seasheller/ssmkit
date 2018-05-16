package com.ssmkit.admin.modules.system.dao;


import com.ssmkit.admin.modules.system.entity.User;
import com.ssmkit.common.dao.BaseDao;

/**
 * 用户Mapper
 * @author 曹亚普
 *
 * @version 2018/05/10
 */
public interface UserDao extends BaseDao<User> {
	User findByUsername(String username);
}