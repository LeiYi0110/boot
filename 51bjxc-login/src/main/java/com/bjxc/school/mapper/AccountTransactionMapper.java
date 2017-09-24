package com.bjxc.school.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bjxc.school.AccountTransaction;
import com.bjxc.school.TransactionMonthSum;

@Mapper
public interface AccountTransactionMapper {
	
	@Select("select id, transaction_type as transactionType, transaction_mny as transactionMny, account_mny as accountMny, transaction_time as transactionDate, is_valid as isValid from t_account_transaction where account_id = #{accountId} and is_valid = 1 order by transaction_time desc")
	List<AccountTransaction> getTransactionByAccountId(@Param("accountId")Integer accountId);
	
	//@Select("select id, transaction_type as transactionType, if(transaction_type in (5,7),CONCAT('+',transaction_mny),CONCAT('-',transaction_mny))  as transactionMnyWithSign, account_mny as accountMny, transaction_time as transactionDate  from t_account_transaction where account_id = #{accountId} order by transaction_time ")
	List<AccountTransaction> getTransactionByAccountIdForApp(@Param("accountId")Integer accountId,@Param("startIndex")Integer startIndex, @Param("length")Integer length);
	
	@Insert("INSERT INTO `51bjxc_micro_service`.`t_account_transaction`(`account_id`,`transaction_type`,`transaction_mny`,`account_mny`,`transaction_time`,`business_id`, is_valid ,`desc`) VALUES (#{accountId},#{transactionType},#{transactionMny},#{accountMny},now(),#{businessId},#{isValid},#{desc}) ")
	Integer addTransaction(AccountTransaction accountTransaction);
	
	List<TransactionMonthSum> getTransactionMonthSum(@Param("accountId")Integer accountId);
}
