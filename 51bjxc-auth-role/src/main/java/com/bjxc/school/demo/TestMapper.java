package com.bjxc.school.demo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestMapper {
	
	TestUser findXML();
	
	@Select("select ID, UserName from tplatformuser where ID = 1")
	TestUser find();

}
