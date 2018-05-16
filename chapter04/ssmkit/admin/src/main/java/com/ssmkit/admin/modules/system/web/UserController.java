package com.ssmkit.admin.modules.system.web;

import com.ssmkit.admin.modules.system.entity.User;
import com.ssmkit.admin.modules.system.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户 Controller
 * 
 * @author 曹亚普
 * 
 * @version 2017/12/28
 *
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

	
	@GetMapping("/getUsers")
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		User user = userService.getByUsername("admin");
		users.add(user);
		return users;
	}
	
	@GetMapping("/getUser")
	public User getUser() {
		User user = userService.findByPrimaryKey(1L);
		
		return user;
	}
	
	@GetMapping("/findUsers")
	public PageInfo<User> findByPage() {
		List<User> users = new ArrayList<User>();
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("username", "admin");
		users = userService.findByPage(param, 1, 10);
	
		PageInfo<User> pageInfo = new PageInfo<User>(users);
		return pageInfo;
	}
	
	@GetMapping("/deleteByExample")
	public int deleteByExample() throws InstantiationException, IllegalAccessException {
		
		Map<String, Object> map = new HashMap<>();
		map.put("username", "123");
		map.put("createBy", "1");
		int flag = userService.deleteByColumnLogically (map);
		return flag;
	}
	
}