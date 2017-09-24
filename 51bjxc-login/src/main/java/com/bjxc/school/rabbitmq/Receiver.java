package com.bjxc.school.rabbitmq;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bjxc.school.Account;
import com.bjxc.school.TopUp;
import com.bjxc.school.mapper.TopUpMapper;
import com.bjxc.school.service.AccountService;
import com.bjxc.userdetails.TokenUserDetailsService;
import com.bjxc.userdetails.UserDetails;


@Component
public class Receiver {
	
	@Resource
	private TopUpMapper topUpMapper;
	
	@Resource
	private AccountService accountService;
	
	@Resource
	private TokenUserDetailsService tokenService;
	
	private static final Logger logger = LoggerFactory.getLogger(Receiver.class);
	
	public void receiveMessage(Map<String, String> message) {
		 
		 /*
		 Integer topUpId = Integer.valueOf(message.get("topUpId")) ;
		 Integer status = Integer.valueOf(message.get("status")) ;
		 
		 TopUp topUp = topUpMapper.getTopUp(topUpId);
		 
		 Account account = accountService.getAccountByAccountId(topUp.getAccountId());
		 
		 accountService.addAccountTransaction(5, topUp.getMny(), account, topUpId);
		 
		 topUpMapper.updateTopUpStatus(topUpId, status);
		 */
		 try
		 {
			 //String token = message.get("token");
			 Integer transactionType = Integer.valueOf(message.get("transactionType"));
			 Integer transactionMny = Integer.valueOf(message.get("transactionMny"));
			 Integer businessId = Integer.valueOf(message.get("businessId"));
			 Integer userId = Integer.valueOf(message.get("userId"));
			 
			 
			 logger.info("transactionType : " + message.get("transactionType"));
			 logger.info("transactionMny : " + message.get("transactionMny"));
			 logger.info("businessId : " + message.get("businessId"));
			 logger.info("userId : " + message.get("userId"));
			 
			 
			 if (transactionType == 6) {
				return;
			 }
			 
			 //UserDetails userDetails = tokenService.loadUserByToken(token);
			 
			 Account account = accountService.getAccount(userId);
			 
			 if (account != null) {
				 accountService.addAccountTransaction(transactionType, transactionMny, account, businessId);
				 if (transactionType == 5) {
					 topUpMapper.updateTopUpStatus(businessId, 1);
				}
			 }
			 
			 
		 }
		 catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
		 }
		 
		 
		 
		 
	}

}
