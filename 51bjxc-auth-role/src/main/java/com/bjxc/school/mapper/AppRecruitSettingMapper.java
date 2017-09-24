package com.bjxc.school.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bjxc.school.AppRecruitSetting;

@Mapper
public interface AppRecruitSettingMapper {
	
	@Select("select id, is_show as isShow, ins_id as insId from t_app_recruit_setting where ins_id = #{insId}")
	AppRecruitSetting getAppRecruitSetting(@Param("insId")Integer insId);
	
	@Insert("insert t_app_recruit_setting(ins_id) values(#{insId})")
	Integer initAppRecruitSetting(@Param("insId")Integer insId);
	
	@Update("update t_app_recruit_setting set is_show = #{isShow} where id = #{id}")
	Integer updateAppRecruitSetting(AppRecruitSetting appRecruitSetting);
	
	
	

}
