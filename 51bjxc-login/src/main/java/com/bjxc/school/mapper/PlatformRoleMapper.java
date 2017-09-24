package com.bjxc.school.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bjxc.school.PlatformRole;

@Mapper
public interface PlatformRoleMapper {
	
	@Select("select id, role_key as roleKey, role_name as roleName, ins_id as insId, ins_code as insCode from t_platform_role where ins_id = #{insId}")
	List<PlatformRole> getPlatformRoleList(@Param("insId")Integer insId);
	
	@Select("select id, role_key as roleKey, role_name as roleName, ins_id as insId, ins_code as insCode from t_platform_role where id = #{roleId}")
	PlatformRole getPlatformRole(@Param("roleId")Integer roleId);


}
