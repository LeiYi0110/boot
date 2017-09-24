package com.bjxc.school.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bjxc.school.Insititution;

@Mapper
public interface InstitutionMapper {
	@Select("select t_institution.id as id, t_institution.Name as insName, t_institution.Create_Time as createDate, t_dict_provinces.province as provinceName, t_dict_cities.city as cityName	from t_institution	left join t_dict_provinces on t_institution.Province = t_dict_provinces.province_Code	left join t_dict_cities on t_institution.City = t_dict_cities.city_Code where solution_id = #{solutionId} ")
	List<Insititution> getInsList(@Param("solutionId")Integer solutionId);
	
	@Select("select t_institution.id as id, t_institution.Name as insName from t_institution inner join t_coach on t_coach.ins_id = t_institution.id where  t_coach.user_id = #{userId}")
	Insititution getCoachIns(@Param("userId")Integer userId);
	
	@Select("select t_institution.id as id, t_institution.Name as insName from t_institution inner join t_student on t_student.ins_id = t_institution.id where  t_student.user_id = #{userId}")
	Insititution getStudentIns(@Param("userId")Integer userId);
	
	@Select("select id, `Name` as insName from t_institution where id = #{insId}")
	Insititution getInstitution(@Param("insId")Integer insId);
	
}
