package com.bjxc.school.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class SwitchUsinInfo extends UsinInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SwitchUsinInfo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		// TODO Auto-generated constructor stub
	}
	
	private UsinInfo baseUser;//父登录用户

	public UsinInfo getBaseUser() {
		return baseUser;
	}

	public void setBaseUser(UsinInfo baseUser) {
		this.baseUser = baseUser;
	}
	
	

}
