package com.bjxc.school.controller;


import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bjxc.Result;
import com.bjxc.school.LoginResult;
import com.bjxc.school.PlatformUser;
import com.bjxc.school.RoleAuth;
import com.bjxc.school.service.PlatformUserService;
import com.bjxc.school.service.RoleAuthService;


@RestController
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Resource
	private PlatformUserService platformUserService;
	
	@Resource
	private RoleAuthService roleAuthService;
	
	//public Result login(String userName, String pwd)
	@RequestMapping(value = "/login",method=RequestMethod.POST)
	public Result login(String userName, String pwd)
	{
		Result result = new Result();
		
		PlatformUser user = platformUserService.getUser(userName, pwd);
		
		if (user == null) {
			result.error("用户不存在");
			return result;
		}
		
		LoginResult loginResult = new LoginResult();
		
		
		String token = platformUserService.getToken(user);
		
		List<RoleAuth> list = roleAuthService.getRoleAuthList(user.getId());
		user.setPassword(null);
		user.setSalt(null);
		loginResult.setAuthList(list);
		loginResult.setToken(token);
		loginResult.setUser(user);
		
		result.success(loginResult);
		logger.info("OK");
		return result;
	}

}
