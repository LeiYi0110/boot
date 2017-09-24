package com.bjxc.school;

import java.util.List;

public class LoginResult {
	
	private PlatformUser user;
	private String token;
	

	
	
	public PlatformUser getUser() {
		return user;
	}

	public void setUser(PlatformUser user) {
		this.user = user;
	}

	private List<RoleAuth> authList;

	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<RoleAuth> getAuthList() {
		return authList;
	}

	public void setAuthList(List<RoleAuth> authList) {
		this.authList = authList;
	}
	
	

}
