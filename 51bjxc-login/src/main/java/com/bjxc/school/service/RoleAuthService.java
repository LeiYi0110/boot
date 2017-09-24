package com.bjxc.school.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bjxc.school.RoleAuth;
import com.bjxc.school.mapper.RoleAuthMapper;

@Service
public class RoleAuthService {
	
	@Resource
	private RoleAuthMapper roleAuthMapper;
	
	public List<RoleAuth> getRoleAuthList(Integer userId)
	{
		return roleAuthMapper.getRoleAuthList(userId);
	}

}
