package com.bjxc.school.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bjxc.school.PlatformRole;

@Mapper
public interface PlatformRoleMapper {
	
	List<PlatformRole> getRoleList(@Param("insId")Integer insId,@Param("startIndex")Integer startIndex, @Param("length")Integer length);
	
	
	
	//@Insert("INSERT INTO `t_platform_role`(`role_key`,`role_name`,`ins_id`,`ins_code`,`update_time`,`op_user_id`) VALUES(#{roleKey},#{roleName},#{insId},#{insCode},now(),#{operatorId}) ")
	Integer addPlatformRole(PlatformRole platformRole);
	
	Integer getRoleListCount(@Param("insId")Integer insId);
	
	@Select("select role_name as roleName from t_platform_role where id = #{id}")
	PlatformRole getRole(@Param("id")Integer roleId);
	
	@Update("update t_platform_role set role_name = #{roleName},update_time = now() where id = #{id}")
	Integer updateRole(PlatformRole platformRole);
	
	@Select("select count(*) from t_platform_user inner join t_platform_user_role on t_platform_user.id = t_platform_user_role.user_id where t_platform_user_role.role_id = #{roleId}")
	Integer roleUserCount(@Param("roleId")Integer roleId);
	
	@Delete("delete from t_platform_role where id = #{roleId}")
	Integer deleteRole(@Param("roleId")Integer roleId);

}
