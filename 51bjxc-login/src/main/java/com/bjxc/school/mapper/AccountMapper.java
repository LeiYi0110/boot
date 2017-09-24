package com.bjxc.school.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.bjxc.school.Account;
import com.bjxc.school.PayRecord;

@Mapper
public interface AccountMapper {
	
	@Insert("INSERT INTO `51bjxc_micro_service`.`t_account`(`user_id`,`password`,`salt`) VALUES(#{userId},#{password},#{salt})")
	Integer addAccount(Account account);
	
	
	@Select("SELECT `t_account`.`id` as id,`t_account`.`user_id` as userId,`t_account`.`password` as password,`t_account`.`salt` as salt,`t_account`.`mny` as mny FROM `51bjxc_micro_service`.`t_account` where user_id = #{userId}")
	Account getAccountByUserId(@Param("userId")Integer userId);
	
	@Select("SELECT `t_account`.`id` as id,`t_account`.`user_id` as userId,`t_account`.`password` as password,`t_account`.`salt` as salt,`t_account`.`mny` as mny FROM `51bjxc_micro_service`.`t_account` where id = #{accountId}")
	Account getAccountByAccountId(@Param("accountId")Integer accountId);
	
	
	@Update("UPDATE `51bjxc_micro_service`.`t_account` SET `user_id` = #{userId}, `password` = #{password},`salt` = #{salt},`mny` = #{mny} WHERE `id` = #{id}")
	Integer updateAccount(Account account);
	
	@Select("SELECT `t_account`.`id` as id,`t_account`.`user_id` as userId,`t_account`.`password` as password,`t_account`.`salt` as salt,`t_account`.`mny` as mny FROM `51bjxc_micro_service`.`t_account` where user_id = #{userId} and password = #{password}")
	Account getAccountByUserIdAndPwd(@Param("userId")Integer userId,@Param("password")String password);
	
	
	
	List<PayRecord> getPayRecord(@Param("userId")Integer userId , @Param("startIndex")Integer startIndex, @Param("length")Integer length);
	
	List<Account> getAccountList(@Param("searchText")String searchText, @Param("startIndex")Integer startIndex, @Param("length")Integer length);
	
	Integer getAccountListCount(@Param("searchText")String searchText);
	
	
	

}
