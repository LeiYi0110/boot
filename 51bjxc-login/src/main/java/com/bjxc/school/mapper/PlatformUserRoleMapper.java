package com.bjxc.school.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.bjxc.school.PlatformUser;
import com.bjxc.school.PlatformUserRole;

@Mapper
public interface PlatformUserRoleMapper {
	
	@Insert("insert t_platform_user_role(user_id, role_id) values(#{userId},#{roleId})")
	Integer addUserRole(PlatformUserRole platformUserRole);
	
	@Update("update t_platform_user_role set user_id = #{userId}, role_id = #{roleId} where id = #{id}")
	Integer updateUserRole(PlatformUserRole platformUserRole);
	
	@Delete("delete from t_platform_user_role where user_id = #{userId}")
	Integer deleteUserRole(@Param("userId")Integer userId);

}
