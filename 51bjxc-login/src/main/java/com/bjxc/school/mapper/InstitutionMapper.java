package com.bjxc.school.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bjxc.school.Institution;

@Mapper
public interface InstitutionMapper {
	
	@Select("select id, `Name` as insName from t_institution where id = #{insId}")
	Institution getInstitution(@Param("insId")Integer insId);

}
