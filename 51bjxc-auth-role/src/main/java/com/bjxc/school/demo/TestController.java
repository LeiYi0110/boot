package com.bjxc.school.demo;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bjxc.Result;
import com.bjxc.school.security.UserService;
import com.bjxc.school.security.UsinInfo;

@RestController
public class TestController {
	
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Resource
	private TestService service;
	
	@Value("${bjxc.user.defaultPassword}")
	private String defaultPassword;
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/test")
	public Result index(String token, String test)
	{
		logger.info(token);
		logger.info(test);
		Result result = new Result();

		UsinInfo user = userService.getUserInfo(token);
		
		if (user == null) {
			result.setCode(700);
			result.setMessage("用户未登录");
			return result;
		}
		
		TestUser testUser = service.getUser();
		result.success(testUser);
		
		logger.info(defaultPassword);
		
		return result;
	}
	
	@RequestMapping(value="/test/post/{id}",method=RequestMethod.POST)
	public Result post(String token, String test, @PathVariable("id")Integer id)
	{
		logger.info(token);
		logger.info(test);
		logger.info(id.toString());
		
		
		Result result = new Result();
		
		

		UsinInfo user = userService.getUserInfo(token);
		
		if (user == null) {
			result.setCode(700);
			result.setMessage("用户未登录");
			return result;
		}
		
		
		//result.setData(service.getUser());
		TestUser testUser = service.getUserXML();
		result.success(testUser);
		
		logger.info(defaultPassword);
		
		return result;
	}

}
