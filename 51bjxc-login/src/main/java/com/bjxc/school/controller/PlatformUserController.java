package com.bjxc.school.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bjxc.Result;
import com.bjxc.school.Institution;
import com.bjxc.school.PlatformRole;
import com.bjxc.school.PlatformUser;
import com.bjxc.school.PlatformUserListResult;
import com.bjxc.school.PlatformUserRole;
import com.bjxc.school.service.PlatformRoleService;
import com.bjxc.school.service.PlatformUserService;
import com.bjxc.userdetails.PlatformUserDetail;
import com.bjxc.userdetails.TokenUserDetailsService;


@RestController
public class PlatformUserController {
	
	@Resource
	private PlatformUserService platformUserService;
	
	@Resource
	private TokenUserDetailsService tokenService;
	
	@Resource
	private PlatformRoleService platformRoleService;
	
	@RequestMapping(value = "/platformUserList",method=RequestMethod.GET)
	public Result getPlatformUserList(String token, String searchText, String role, Integer startIndex, Integer length)
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
				List<PlatformUser> list = platformUserService.getUserList(searchText,role,startIndex,length,userDetail.getInsId());
				Integer count = platformUserService.getUserListCount(searchText, role,userDetail.getInsId());
				
				PlatformUserListResult userListResult = new PlatformUserListResult();
				
				userListResult.setList(list);
				userListResult.setCount(count);
				
				result.success(userListResult);
			}
			
		}
		catch(Exception ex)
		{
			result.error(ex.getMessage());
			
		}
		return result;
		
		
	}
	
	@RequestMapping(value = "/addPlatformUser", method = RequestMethod.POST)
	public Result addPlarformUser(String token,String userName,String mobile, Integer roleId, Integer insId, Integer areaId, Integer stationId)
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
				
				if (platformUserService.isExistsUser(userName)) {
					
					result.setCode(701);
					result.setMessage("该用户名已存在");
					
				}
				else
				{
					String password = "123456";
					
					PlatformUser user = new PlatformUser();
					
					user.setUserName(userName);
					user.setPassword(password);
					user.setInsId(userDetail.getInsId());
					user.setAreaId(areaId);
					user.setStationId(stationId);
					user.setStatus(1);
					user.setMobile(mobile);
					
					Integer row = platformUserService.addUser(user, roleId);
					
					if (row == 1) {
						
						result.success(row);
						
					}
					else
					{
						result.error(row, "insert data failed");
					}
				}
				
			}
		}
		catch(Exception ex)
		{
			result.error(ex.getMessage());
		}
		
		
		return result;
	}
	@RequestMapping(value="/updatePlatformUser", method=RequestMethod.POST)
	public Result updatePlatformUser(String token,String userName,String mobile, Integer roleId, Integer insId, Integer areaId, Integer stationId,Integer userRoleId,Integer userId)
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
				
				PlatformUser user = new PlatformUser();
				
				user.setUserName(userName);
				user.setInsId(insId);
				user.setAreaId(areaId);
				user.setStationId(stationId);
				user.setStatus(1);
				user.setMobile(mobile);
				user.setId(userId);
				
				PlatformUserRole userRole = new PlatformUserRole();
				userRole.setId(userRoleId);
				userRole.setUserId(userId);
				userRole.setRoleId(roleId);
				
				
				
				Integer row = platformUserService.updateUser(user, userRole);
				
				if (row == 1) {
					
					result.success(row);
					
				}
				else
				{
					result.error(row, "insert data failed");
				}
				
			}
		}
		catch(Exception ex)
		{
			result.error(ex.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value="/deleteUser", method = RequestMethod.POST)
	public Result deleteUser(String token, Integer userId)
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
				Integer row = platformUserService.deleteUser(userId);
				
				if (row == 1) {
					
					result.success(row);
					
				}
				else
				{
					result.error(row, "insert data failed");
				}
			}
		}
		catch(Exception ex)
		{
			result.error(ex.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value="/initInsPlatformUser",method=RequestMethod.POST)
	public Result initInsPlatformUser(String token, Integer roleId, String userName, String mobile){
		Result result = new Result();
		try
		{
			PlatformUserDetail platformUserDetail = tokenService.loadPlatformUserByToken(token);
			if (platformUserDetail.getInsId().intValue() != 0) {
				result.error("只有平台管理员有此权限");
			}
			else
			{
				PlatformUser user = new PlatformUser();
				PlatformRole platformRole = platformRoleService.getRole(roleId);
				Institution institution = platformUserService.getInstitution(platformRole.getInsId());
				user.setUserName(userName);
				user.setInsId(platformRole.getInsId());
				user.setStatus(1);
				user.setMobile(mobile);
				
				Integer row = platformUserService.addUser(user, roleId);
				
				if (row == 1) {
					
					result.success(row);
					
				}
				else
				{
					result.error(row, "insert data failed");
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

}
