package com.bjxc.school.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bjxc.Result;
import com.bjxc.school.service.PlatformRoleService;
import com.bjxc.userdetails.PlatformUserDetail;
import com.bjxc.userdetails.TokenUserDetailsService;

@RestController
public class PlatformRoleController {
	
	@Resource
	private PlatformRoleService platformRoleService;
	
	@Resource
	private TokenUserDetailsService tokenService;
	
	@RequestMapping(value="/roles")
	public Result getRoleList(String token)
	{
		Result result = new Result();
		
		try
		{
			PlatformUserDetail userDetail = tokenService.loadPlatformUserByToken(token);
			
			if (userDetail == null) {
				result.error("用户未登录");
			}
			else
			{
				Integer insId = userDetail.getInsId();
				
				result.success(platformRoleService.getRoleList(insId));
			}
		}
		catch(Exception ex)
		{
			result.error(ex.getMessage());
		}
		
		return result;
	}

}
