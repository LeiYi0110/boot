package com.bjxc.school.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bjxc.school.TopUp;

@Mapper
public interface TopUpMapper {
	
	//@Insert("INSERT INTO `51bjxc_micro_service`.`t_top_up`(`mny`,`account_id`,`create_time`) VALUES(#{mny},#{accountId},now())")
	Integer addTopUp(TopUp topUp);
	
	@Update("update t_top_up set status = #{status} where id = #{id}")
	Integer updateTopUpStatus(@Param("id")Integer id, @Param("status")Integer status);
	
	@Select("select id, mny, account_id as accountId, create_time as createTime, status from t_top_up where id = #{id}")
	TopUp getTopUp(@Param("id")Integer id);

}
