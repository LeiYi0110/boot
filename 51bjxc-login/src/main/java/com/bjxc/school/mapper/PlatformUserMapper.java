package com.bjxc.school.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bjxc.school.PlatformUser;

@Mapper
public interface PlatformUserMapper {
	
	//@Select("select id,user_name as userName, password, '1' as inscode, 1 as insId, salt,ins_area_id as areaId, ins_station_id as stationId, mobile from t_platform_user where user_name = #{user_name}")
	PlatformUser getUser(@Param("user_name")String user_name);
	
	List<PlatformUser> getUserList(@Param("searchText")String searchText,@Param("role")String role, @Param("startIndex")Integer startIndex, @Param("length")Integer length, @Param("insId")Integer insId);
	
	Integer getUserListCount(@Param("searchText")String searchText,@Param("role")String role,@Param("insId")Integer insId);
	
	
//	@Insert("")
	Integer addUser(PlatformUser user);
	
	
	@Update("update t_platform_user set user_name = #{userName}, mobile = #{mobile}, ins_area_id = #{areaId}, ins_station_id = #{stationId} where id = #{id}")
	Integer updateUser(PlatformUser user);
	
	@Delete("delete from t_platform_user where id = #{userId}")
	Integer deleteUser(@Param("userId")Integer userId);
	
	@Select("select count(*) from t_platform_user where user_name = #{userName}")
	Integer existsUser(@Param("userName")String userName);
	
}
