package com.bjxc.school.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bjxc.school.BankCard;

@Mapper
public interface BankCardMapper {
	
	@Insert("insert t_bank_card(card_no,add_time,account_id,bank_id,card_holder) values(#{cardNo},now(),#{accountId},#{bankId},#{cardHolder})")
	Integer addBankCard(BankCard bankCard);
	
	@Select("select t_bank_card.id,card_no as cardNo,add_time as addTime,account_id as accountId,bank_id as bankId,card_holder as cardHolder , t_bank.bank_name as bankName from t_bank_card  inner join t_account on t_bank_card.account_id = t_account.id inner join t_bank on t_bank_card.bank_id = t_bank.id where t_account.user_id = #{userId}")
	List<BankCard> getBankCardList(@Param("userId")Integer userId);
	
	@Delete("delete from t_bank_card where id = #{bankCardId}")
	Integer deleteBankCard(@Param("bankCardId")Integer bankCardId);
	
	@Select("select t_bank_card.id,card_no as cardNo,add_time as addTime,account_id as accountId,bank_id as bankId,card_holder as cardHolder , t_bank.bank_name as bankName from t_bank_card  inner join t_account on t_bank_card.account_id = t_account.id inner join t_bank on t_bank_card.bank_id = t_bank.id where t_account.id = #{accountId} and t_bank_card.card_no = #{bankCardNo}")
	BankCard getBankCardInfoByCardNo(@Param("accountId")Integer accountId, @Param("bankCardNo")String bankCardNo);

}
