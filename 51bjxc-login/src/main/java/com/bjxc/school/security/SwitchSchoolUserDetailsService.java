package com.bjxc.school.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SwitchSchoolUserDetailsService extends SchoolUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsinInfo userDetails = (UsinInfo)super.loadUserByUsername(username);

		List<GrantedAuthority> grantedAuthorities = userDetails.getAuthorities().stream().collect(Collectors.toList());

		UsinInfo usinInfo = (UsinInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// 不是超级管理员不能模拟登录
		if (usinInfo.getAuthorities().stream().filter(f -> f.getAuthority().equals("ROLE_PLATFORM_MANAGER")).count() <= 0) {
			throw new UsernameNotFoundException("Error in user role");
		}

		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_PLATFORM_SWITCH"));

		SwitchUsinInfo switchUsinInfo = new SwitchUsinInfo(userDetails.getUsername(), userDetails.getPassword(),
				grantedAuthorities);
		
		switchUsinInfo.setInsId(userDetails.getInsId());
		switchUsinInfo.setInsName(userDetails.getInsName());
		switchUsinInfo.setStationId(userDetails.getStationId());
		switchUsinInfo.setId(userDetails.getId());
		switchUsinInfo.setInsCode(userDetails.getInsCode());
		switchUsinInfo.setRole(userDetails.getRole());
		
		return switchUsinInfo;
	}
}
