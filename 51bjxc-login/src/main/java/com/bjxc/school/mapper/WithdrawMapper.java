package com.bjxc.school.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.bjxc.school.Withdraw;


@Mapper
public interface WithdrawMapper {
	
	//@Insert("insert t_withdraw(account_id,mny,apply_time,status, bank_card_id) values(#{accountId},#{mny},now(),0,#{bankCardId})")
	Integer addWithdraw(Withdraw withdraw);
	
	
	List<Withdraw> getWithdrawList(@Param("status")Integer status,@Param("startIndex")Integer startIndex,@Param("length")Integer length, @Param("startTime")Date startTime,@Param("endTime")Date endTime);
	
	Integer getWithdrawListCount(@Param("status")Integer status,@Param("startTime")Date startTime,@Param("endTime")Date endTime);
	
	@Update("update t_withdraw set status = #{status} where id = #{id}")
	Integer updateWithdrawStatus(@Param("id")Integer id, @Param("status")Integer status);

}
