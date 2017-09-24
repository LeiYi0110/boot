package com.bjxc.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bjxc.json.JacksonBinder;

/**
 * ͨ��token��ȡ�û���Ϣ�ĸ�����
 * @author cras  
 */

public class TokenUserDetailsService {
	private static JacksonBinder binder = JacksonBinder.buildNormalBinder();
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	
	
	public StringRedisTemplate getRedisTemplate() {
		return redisTemplate;
	}
	

	public UserDetails loadUserByToken(String token){
		String info = (String)redisTemplate.opsForHash().get(StaticNature.userRedis, token);
		if(StringUtils.isEmpty(info)){
			throw new UnauthorizedTokenException("token error");
		}
		UserDetails userInfo=binder.fromJson(info, UserDetails.class);
		return userInfo;
	}
	
	public PlatformUserDetail loadPlatformUserByToken(String token){
		String info = (String)redisTemplate.opsForHash().get(StaticNature.platformUserRedis, token);
		if(StringUtils.isEmpty(info)){
			throw new UnauthorizedTokenException("token error");
		}
		PlatformUserDetail userInfo=binder.fromJson(info, PlatformUserDetail.class);
		return userInfo;
	}

	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}
