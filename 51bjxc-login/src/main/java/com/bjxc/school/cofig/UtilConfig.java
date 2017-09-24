package com.bjxc.school.cofig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bjxc.userdetails.TokenUserDetailsService;

@Configuration
public class UtilConfig {
	
	@Bean
	public TokenUserDetailsService tokenService()
	{
		return new TokenUserDetailsService();
	}

}
