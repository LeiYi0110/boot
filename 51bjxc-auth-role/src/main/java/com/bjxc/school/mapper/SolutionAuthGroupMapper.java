package com.bjxc.school.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bjxc.school.SolutionAuthGroup;

@Mapper
public interface SolutionAuthGroupMapper {
	
	@Select("select t_solution_auth_group.solution_id as solutionId, t_platform_authority_group.id as authGroupId, t_platform_authority_group.group_name as authGroupName from  t_solution_auth_group inner join t_platform_authority_group on t_solution_auth_group.auth_group_id = t_platform_authority_group.id")
	List<SolutionAuthGroup> getSolutionAuthGroup();
	
	@Insert("INSERT INTO `51bjxc_micro_service`.`t_solution_auth_group`(`solution_id`,`auth_group_id`) values(#{solutionId},#{authGroupId})")
	Integer addSolutionAuthGroup(@Param("solutionId")Integer solutionId, @Param("authGroupId")Integer authGroupId);
	
	@Select("select t_platform_authority_group.id as authGroupId, t_platform_authority_group.group_name as authGroupName from  t_platform_authority_group")
	List<SolutionAuthGroup> getSolutionAuthGroupList();
	
	
	
	List<SolutionAuthGroup> getFunctionConfigSolutionAuthGroupList(@Param("insId")Integer insId);
	
	List<SolutionAuthGroup> getSchoolSolutionAuthGroupList(@Param("insId")Integer insId);
	
	
	
	
	/*
	@Delete("delete from t_solution_auth_group where id = #{id}")
	Insert
	*/

}
