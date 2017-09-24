package com.bjxc.school.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.bjxc.school.Bank;

@Mapper
public interface BankMapper {
	
	@Select("select id, bank_name as bankName from t_bank order by id")
	List<Bank> getBankList();
	

}
