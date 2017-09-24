package com.bjxc.school.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bjxc.userdetails.TokenUserDetailsService;

@Service
public class UserService {
	
	@Autowired
	private TokenUserDetailsService tokenService;
	/*
	@Resource
	private TokenUserDetailsService tokenService;
	*/
	
	
	public UsinInfo getUserInfo(String token)
	{
		try{
			token = "D9E92CD6AA6EF8663FF6096AC9045AE6CC9F70A56D0B9CD0FD6E686A6A40C8CE";
			tokenService.loadUserByToken(token);
			
			List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
			authList.add(new SimpleGrantedAuthority("ROLE_PLATFORM_MANAGER"));
			authList.add(new SimpleGrantedAuthority("ROLE_PLATFORM_USER"));
			UsinInfo info = new UsinInfo("欧雪映", "123456", authList);
			return info;
			
		}
		catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
		
		
	}
	

}
