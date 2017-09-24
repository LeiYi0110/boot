package com.bjxc.school.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bjxc.Result;
import com.bjxc.school.Insititution;
import com.bjxc.school.ListResult;
import com.bjxc.school.PlatformRole;
import com.bjxc.school.service.AuthService;
import com.bjxc.school.service.PlatformRoleService;
import com.bjxc.userdetails.PlatformUserDetail;
import com.bjxc.userdetails.TokenUserDetailsService;

@RestController
public class PlatformRoleController {
	
	private static final Logger logger = LoggerFactory.getLogger(PlatformRoleController.class);
	
	@Resource
	private PlatformRoleService platformRoleService;
	
	@Resource
	private AuthService authService;
	
	@Resource
	private TokenUserDetailsService tokenService;
	
	@RequestMapping(value="/roleList")
	public Result getPlatformRoleList(String token, Integer startIndex, Integer length)
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
				List<PlatformRole> list = platformRoleService.getPlatformRoleList(userDetail.getInsId(),startIndex,length);
				Integer count = platformRoleService.getPlatformRoleListCount(userDetail.getInsId());
				
				ListResult listResult = new ListResult();
				listResult.setCount(count);
				listResult.setList(list);
				result.success(listResult);
				
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
		
	
	@RequestMapping(value="/addRole", method=RequestMethod.POST)
	public Result addRole(String token, String roleName, String auths)
	{
	
		logger.info("addRole");
		Result result = new Result();
		
		try
		{
			PlatformUserDetail userDetail = tokenService.loadPlatformUserByToken(token);
			if (userDetail == null) {
				
				result.error("用户未登录");
			}
			else
			{
				String[] authArray = auths.split(",");
				
				PlatformRole platformRole = new PlatformRole();
				platformRole.setInsCode(userDetail.getInsCode());
				platformRole.setInsId(userDetail.getInsId());
				platformRole.setOperatorId(userDetail.getId());
				platformRole.setRoleName(roleName);
				platformRole.setRoleKey("Role_" + String.valueOf((new Date()).getTime()));
				
				
				platformRoleService.addPlatformRole(platformRole, authArray);
				
				result.success("success");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		
		
		return result;
	}
	@RequestMapping(value="/updateRole", method=RequestMethod.POST)
	public Result updateRole(String token, Integer roleId, String roleName, String auths)
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
				String[] authArray = auths.split(",");
				
				PlatformRole platformRole = new PlatformRole();
				platformRole.setId(roleId);
				
				platformRole.setRoleName(roleName);
				platformRole.setRoleKey("Role_" + String.valueOf((new Date()).getTime()));
				
				
				//platformRoleService.addPlatformRole(platformRole, authArray);
				platformRoleService.updatePlatformRole(platformRole, authArray);
				
				result.success("success");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		return result;
	}
	@RequestMapping(value="/initInsManagementRole", method=RequestMethod.POST)
	public Result initInsManagementRole(String token, Integer insId)
	{
		Result result = new Result();
		try
		{
			PlatformUserDetail platformUserDetail = tokenService.loadPlatformUserByToken(token);
			
			if (platformUserDetail.getInsId().intValue() != 0) {
				result.error("需要平台管理员权限");
			}
			else
			{
				String[] authArray = authService.getAllAuthId();
				
				PlatformRole platformRole = new PlatformRole();
//				platformRole.setInsCode(userDetail.getInsCode());
				platformRole.setInsId(insId);
				platformRole.setOperatorId(0);
				Insititution insititution = platformRoleService.getInstitution(insId);
				String insName = "";
				if (insititution == null) {
					insName = "平台管理员";
				}
				else
				{
					insName = insititution.getInsName();
				}
				platformRole.setRoleName(insName + "管理员");
				platformRole.setRoleKey("Role_" + String.valueOf((new Date()).getTime()));
				
				
				platformRoleService.addPlatformRole(platformRole, authArray);
				result.success(platformRole);
			}
			
			
			//result.success("success");
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "/deleteRole", method=RequestMethod.POST)
	public Result deleteRole(String token, Integer roleId)
	{
		Result result = new Result();
		
		try
		{
			PlatformUserDetail platformUserDetail = tokenService.loadPlatformUserByToken(token);
			Integer roleUserCount = platformRoleService.getRoleUserCount(roleId);
			if (roleUserCount.intValue() > 0) {
				result.error("此角色有用户");
				result.setCode(701);
			}
			else
			{
				platformRoleService.deleteRole(roleId);
				result.success("删除成功");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		
		return result;
	}
	
}
