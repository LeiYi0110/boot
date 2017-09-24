package com.bjxc.school.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bjxc.school.AuthDB;
import com.bjxc.school.Authority;

@Mapper
public interface AuthMapper {
	
	List<AuthDB> getAuthList();
	
	@Select("select id, authority_name as name, authority_level as level, parent_id as parentId from t_platform_authority order by authority_level,order_no")
	List<Authority> getAuthorityList();
	@Select("select  t_platform_authority.id, authority_name as name, authority_level as level, parent_id as parentId,  if(t_platform_role_authority.authority_id is null, 0, 1) as checked from  t_platform_authority left  join t_platform_role_authority on t_platform_authority.id = t_platform_role_authority.authority_id and t_platform_role_authority.role_id = #{roleId} order by authority_level, order_no")
	List<Authority> getRoleAuthList(@Param("roleId")Integer roleId);
	
	

}
