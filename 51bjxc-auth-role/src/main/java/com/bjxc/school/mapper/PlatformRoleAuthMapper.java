package com.bjxc.school.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bjxc.school.PlatformRoleAuth;


@Mapper
public interface PlatformRoleAuthMapper {
	
	@Insert("insert t_platform_role_authority(role_id,authority_id) values(#{roleId},#{authorityId})")
	Integer addPlatformRoleAuth(PlatformRoleAuth platformRoleAuth);
	
	@Delete("delete from t_platform_role_authority where id = #{id}")
	Integer deleteRoleAuth(@Param("id")Integer roleAuthId);
	
	@Select("select id, role_id as roleId,  authority_id as authorityId from t_platform_role_authority where role_id = #{roleId}")
	List<PlatformRoleAuth> getRoleAuthIds(@Param("roleId")Integer roleId);

}
