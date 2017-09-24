package com.bjxc.school.demo;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class TestService {
	
	@Resource
	private TestMapper mapper;
	
	public TestUser getUser()
	{
		return mapper.find();
	}
	public TestUser getUserXML()
	{
		return mapper.findXML();
	}

}
