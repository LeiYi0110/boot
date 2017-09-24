package com.bjxc.school.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.bjxc.json.JacksonBinder;
import com.bjxc.school.Institution;
import com.bjxc.school.PlatformUser;
import com.bjxc.school.PlatformUserRole;
import com.bjxc.school.StaticNature;
import com.bjxc.school.mapper.InstitutionMapper;
import com.bjxc.school.mapper.PlatformUserMapper;
import com.bjxc.school.mapper.PlatformUserRoleMapper;
import com.bjxc.school.service.encode.Md5PasswordEncoder;
import com.bjxc.school.utils.CommonUtil;
import com.bjxc.userdetails.TokenUserDetailsService;

@Service
public class PlatformUserService {
	
	private static final Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
	
	@Resource
	private PlatformUserMapper platformUserMapper;
	
	@Resource
	private InstitutionMapper institutionMapper;
	
	@Resource 
	private TokenUserDetailsService tokenService;
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Resource
	private PlatformUserRoleMapper platformUserRoleMapper;
	
	private static JacksonBinder binder = JacksonBinder.buildNormalBinder();
	
	private static final Logger logger = LoggerFactory.getLogger(PlatformUserService.class);
	
	public PlatformUser getUser(String user_name, String password)
	{
		PlatformUser platformUser = platformUserMapper.getUser(user_name);
		
		if (platformUser == null) {
			return null;
		}
		
		String pwd = md5PasswordEncoder.encodePassword(password, platformUser.getSalt());
		
		logger.info("pwd is " + pwd);
		
		if (pwd.equals(platformUser.getPassword()) ) {
			
			return platformUser;
		}
		
		return null; 
	}
	
	public String getToken(PlatformUser user)
	{
		String token=CommonUtil.createToken(user.getId());  //登录令牌
		String val=getRedis(StaticNature.platformUserIdTokenRedis,user.getId());
		
//		PlatformUserDetail detail = tokenService.loadPlatformUserByToken(val);

		
		if(val!=null){
			
			redisTemplate.opsForHash().delete(StaticNature.platformUserRedis, val);
			redisTemplate.opsForHash().delete(StaticNature.platformUserIdTokenRedis, user.getId()+"");

		}
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.clear();
		map.put("id", user.getId());
		map.put("userName",user.getUserName());
		map.put("insCode", user.getInscode());
		map.put("insId", user.getInsId());
		map.put("mobile", user.getMobile());
		map.put("areaId", user.getAreaId());
		map.put("stationId", user.getStationId());
		map.put("status", user.getStatus());
		map.put("date", CommonUtil.getSimpleDate(new Date(), "yyyy:MM:dd HH:mm:ss"));
		redisTemplate.opsForHash().put(StaticNature.platformUserRedis, token, binder.toJson(map));
		redisTemplate.opsForHash().put(StaticNature.platformUserIdTokenRedis, user.getId()+"", token);
		logger.info("login user : " + user.getId());
		return token;
	}
	private String getRedis(String field,Object key) {
    	Object result=redisTemplate.opsForHash().get(field, key+"");
    	if(result==null){
    		return null;
    	}
    	return (String)result;
	}
	
	public List<PlatformUser> getUserList(String searchText,String role, Integer startIndex, Integer length, Integer insId)
	{
		return platformUserMapper.getUserList(searchText,role,startIndex,length,insId);
	}
	
	public Integer getUserListCount(String searchText,String role, Integer insId)
	{
		return platformUserMapper.getUserListCount(searchText,role,  insId);
	}
	
	
	public Integer addUser(PlatformUser platformUser, Integer roleId)
	{
		try
		{
			String salt = generateSalt();
			String pwd = md5PasswordEncoder.encodePassword("123456", salt);
			platformUser.setPassword(pwd);
			platformUser.setSalt(salt);
			
			platformUserMapper.addUser(platformUser);
			
			
			
			PlatformUserRole userRole = new PlatformUserRole();
			userRole.setUserId(platformUser.getId());
			userRole.setRoleId(roleId);
			
			platformUserRoleMapper.addUserRole(userRole);
			
			return 1;
		}
		catch (Exception e) {
			// TODO: handle exception
			
			return 0;
		}
	}
	
	public Integer updateUser(PlatformUser user, PlatformUserRole platformUserRole)
	{
		try
		{
			platformUserMapper.updateUser(user);
			platformUserRoleMapper.updateUserRole(platformUserRole);
			
			return 1;
		}
		catch(Exception ex){
			return 0;
		}
	}
	
	public Integer deleteUser(Integer userId)
	{
		try
		{
			platformUserMapper.deleteUser(userId);
			platformUserRoleMapper.deleteUserRole(userId);
			
			return 1;
		}
		catch(Exception ex)
		{
			return 0;
		}
	}
	
	public String generateSalt()
	{
		int max=Integer.MAX_VALUE;
        int min=Integer.MAX_VALUE/10;
        Random random = new Random();

        Integer s = random.nextInt(max)%(max-min+1) + min;
        
        return s.toString();
        
	}
	
	public Boolean isExistsUser(String userName)
	{
		try {
			
			Integer count = platformUserMapper.existsUser(userName);
			
			if (count > 0) {
				return true;
			}
			return false;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return true;
	}
	
	public Institution getInstitution(Integer insId) {
		
		return institutionMapper.getInstitution(insId);
		
	}
	
	

}
