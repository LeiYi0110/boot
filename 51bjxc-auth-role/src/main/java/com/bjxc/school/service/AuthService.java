package com.bjxc.school.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bjxc.school.Auth;
import com.bjxc.school.AuthDB;
import com.bjxc.school.Authority;
import com.bjxc.school.mapper.AuthMapper;

@Service
public class AuthService {
	
	@Resource
	private AuthMapper authMapper;
	/*
	public List<Authority> getRoleAuthList(Integer roleId)
	{
		return authMapper.getRoleAuthList(roleId);//convertData(authMapper.getRoleAuthList(roleId));//authMapper.getRoleAuthList(roleId);
	}
	*/
	public List<Auth> getRoleAuthList(Integer roleId)
	{
		return convertData(authMapper.getRoleAuthList(roleId));
	}
	private List<Auth> convertData(List<Authority> dbList)
	{
		List<Auth> list = new ArrayList<Auth>();
		
		//List<Authority> dbList = authMapper.getAuthorityList();
		
		Map<Integer, Auth> map = new HashMap<Integer, Auth>();
		
		for (Authority authority : dbList) {
			Auth auth = new Auth();
			auth.setId(authority.getId());
			auth.setName(authority.getName());
			auth.setLevel(authority.getLevel());
			auth.setChecked(authority.getChecked());
			auth.setList(new ArrayList<Auth>());
			
			map.put(auth.getId(), auth);
			
			if (authority.getParentId() == null || authority.getParentId() < 0) {
				if (authority.getParentId() == null) {
					list.add(auth);
				}
			}
			else
			{
				if (map.get(authority.getParentId()) != null) {
					map.get(authority.getParentId()).getList().add(auth);
				}
				
			}
		}
		
		return list;
	}
	public List<Auth> getAuthList()
	{
		return convertData(authMapper.getAuthorityList());
	}
	
	public String[] getAllAuthId()
	{
		List<Authority> list = authMapper.getAuthorityList();
		String[] authArray = new String[list.size()];
		
		for(int i = 0; i < list.size(); i++)
		{
			authArray[i] = list.get(i).getId().toString();
		}
		
		return authArray;
	}
	

}
