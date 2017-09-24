package com.bjxc.school.service;


import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Update;
import org.hibernate.validator.internal.util.privilegedactions.GetResolvedMemberMethods;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bjxc.school.Insititution;
import com.bjxc.school.PlatformRole;
import com.bjxc.school.PlatformRoleAuth;
import com.bjxc.school.mapper.InstitutionMapper;
import com.bjxc.school.mapper.PlatformRoleAuthMapper;
import com.bjxc.school.mapper.PlatformRoleMapper;

@Service
public class PlatformRoleService {
	
	@Resource
	private PlatformRoleMapper platformRoleMapper;
	
	@Resource 
	private PlatformRoleAuthMapper platformRoleAuthMapper;
	
	@Resource
	private InstitutionMapper institutionMapper;
	
	public List<PlatformRole> getPlatformRoleList(Integer insId,Integer startIndex, Integer length)
	{
		return platformRoleMapper.getRoleList(insId,startIndex,length);
	}
	
	public Integer getPlatformRoleListCount(Integer insId)
	{
		return platformRoleMapper.getRoleListCount(insId);
	}
	public PlatformRole getRole(Integer roleId)
	{
		return platformRoleMapper.getRole(roleId);
	}
	
	public Integer updatePlatformRole(PlatformRole role, String[] authArray)
	{
		try
		{
			platformRoleMapper.updateRole(role);
			
			List<PlatformRoleAuth> roleAuthsList =  platformRoleAuthMapper.getRoleAuthIds(role.getId());
			
			for (PlatformRoleAuth platformRoleAuth : roleAuthsList) {
				
				platformRoleAuthMapper.deleteRoleAuth(platformRoleAuth.getId());
			}
			
			for(String authItem:authArray)
			{
				PlatformRoleAuth roleAuthItem = new PlatformRoleAuth();
				roleAuthItem.setRoleId(role.getId());
				roleAuthItem.setAuthorityId(Integer.parseInt(authItem));
				platformRoleAuthMapper.addPlatformRoleAuth(roleAuthItem);
			}
			
			return 1;
		}
		catch (Exception e) {
			// TODO: handle exception
			
			return 0;
		}
	}
	@Transactional
	public Integer addPlatformRole(PlatformRole role, String[] authArray)
	{
	
		try
		{
			platformRoleMapper.addPlatformRole(role);
			
			for(String authItem:authArray)
			{
				if (Integer.parseInt(authItem) == 1583) {//平台管理员的权限
					continue;
				}
				PlatformRoleAuth roleAuthItem = new PlatformRoleAuth();
				roleAuthItem.setRoleId(role.getId());
				roleAuthItem.setAuthorityId(Integer.parseInt(authItem));
				platformRoleAuthMapper.addPlatformRoleAuth(roleAuthItem);
			}
			
			return 1;
		}
		catch (Exception e) {
			// TODO: handle exception
			
			return 0;
		}
		
	}
	
	public Insititution getInstitution(Integer insId) {
		
		return institutionMapper.getInstitution(insId);
		
	}
	
	public Integer getRoleUserCount(Integer roleId) {
		return platformRoleMapper.roleUserCount(roleId);
	}
	public Integer deleteRole(Integer roleId)
	{
		return platformRoleMapper.deleteRole(roleId);
	}
	

}
