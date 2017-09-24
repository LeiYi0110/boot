package com.bjxc.school.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bjxc.school.CoachAppSetting;

@Mapper
public interface CoachAppSettingMapper {
	
	@Insert("insert t_coach_app_setting(ins_id) values(#{insId})")
	Integer initSetting(@Param("insId")Integer insId);
	
	@Select("select id,publish_appointment as publishAppointment, assign_student as assignStudent, max_publish_days as maxPublishDays,ins_id as insId from t_coach_app_setting where ins_id = #{insId}")
	CoachAppSetting getInsCoachAppSetting(@Param("insId")Integer insId);
	
	@Update("update t_coach_app_setting set publish_appointment = #{publishAppointment}, assign_student = #{assignStudent}, max_publish_days = #{maxPublishDays}, ins_id = #{insId} where id = #{id}")
	Integer updateCoachAppSetting(CoachAppSetting coachAppSetting);
}
