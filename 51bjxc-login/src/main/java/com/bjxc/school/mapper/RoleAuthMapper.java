package com.bjxc.school.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bjxc.school.RoleAuth;

@Mapper
public interface RoleAuthMapper {
	
	List<RoleAuth> getRoleAuthList(@Param("userId")Integer userId);

}
