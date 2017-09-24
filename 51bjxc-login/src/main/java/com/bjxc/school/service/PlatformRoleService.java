package com.bjxc.school.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bjxc.school.PlatformRole;
import com.bjxc.school.mapper.PlatformRoleMapper;

@Service
public class PlatformRoleService {
	
	@Resource
	private PlatformRoleMapper platformRoleMapper;
	
	public List<PlatformRole> getRoleList(Integer insId)
	{
		return platformRoleMapper.getPlatformRoleList(insId);
	}
	
	public PlatformRole getRole(Integer roleId) {
		
		return platformRoleMapper.getPlatformRole(roleId);
		
	}

}
