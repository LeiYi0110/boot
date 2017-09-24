package com.bjxc.school.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bjxc.Result;
import com.bjxc.school.Auth;
import com.bjxc.school.Authority;
import com.bjxc.school.PlatformRole;
import com.bjxc.school.PlatformRoleResult;
import com.bjxc.school.service.AuthService;
import com.bjxc.school.service.PlatformRoleService;

@RestController
public class AuthController {
	@Resource
	private AuthService authService;
	
	@Resource
	private PlatformRoleService platformRoleService;
	
	@RequestMapping(value="/authList")
	public Result getAuthList()
	{
		Result result = new Result();
		
		List<Auth> list = authService.getAuthList();
		
		result.success(list);
		
		return result;
	}
	
	@RequestMapping(value="/roleAuthList")
	public Result getRoleAuthList(Integer roleId)
	{
		Result result = new Result();
		
		PlatformRole role = platformRoleService.getRole(roleId);
		List<Auth> list = authService.getRoleAuthList(roleId);
		
		PlatformRoleResult roleResult = new PlatformRoleResult();
		roleResult.setList(list);
		roleResult.setPlatformRole(role);
		
		result.success(roleResult);
		
		return result;
	}
	
	

}
