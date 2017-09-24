package com.bjxc.school.service;



import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bjxc.school.Account;
import com.bjxc.school.AccountTransaction;
import com.bjxc.school.Bank;
import com.bjxc.school.BankCard;
import com.bjxc.school.PayRecord;
import com.bjxc.school.TopUp;
import com.bjxc.school.TransactionMonthSum;
import com.bjxc.school.Withdraw;
import com.bjxc.school.exception.AuthenticateException;
import com.bjxc.school.mapper.AccountMapper;
import com.bjxc.school.mapper.AccountTransactionMapper;
import com.bjxc.school.mapper.BankCardMapper;
import com.bjxc.school.mapper.BankMapper;
import com.bjxc.school.mapper.TopUpMapper;
import com.bjxc.school.mapper.WithdrawMapper;
import com.bjxc.school.service.encode.Md5PasswordEncoder;

@Service
public class AccountService {
	private static final Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
	@Resource
	private BankMapper bankMapper;
	
	@Resource 
	private AccountMapper accountMapper;
	
	@Resource
	private BankCardMapper bankCardMapper;
	
	@Resource
	private PlatformUserService platformUserService;
	
	
	@Resource
	private TopUpMapper topUpMapper;
	
	@Resource
	private WithdrawMapper withdrawMapper;
	
	@Resource
	private AccountTransactionMapper accountTransactionMapper;
	
	public List<Bank> getBankList(){
		
		return bankMapper.getBankList();
	}
	
	private Account setPassword(String password, Integer userId) {
		
		Account account = accountMapper.getAccountByUserId(userId);
		
		String salt = platformUserService.generateSalt();
		password = md5PasswordEncoder.encodePassword(password, salt);
		
		if (account == null) {
			
			Account newAccount = new Account();
			
			
			
			newAccount.setUserId(userId);
			newAccount.setPassword(password);
			newAccount.setSalt(salt);
			
			accountMapper.addAccount(newAccount);
			account = newAccount;
		}
		else
		{
			account.setPassword(password);
			account.setSalt(salt);
			accountMapper.updateAccount(account);
		}
		
		return account;
		
	}
	
	public Account createAccount(String password, Integer userId){
		
		//setPassword(password, userId)
		
		if (checkPassword(password, userId) == null) {
			
			return setPassword(password, userId);
			
		}
		return null;
	}
	public Boolean changePassword(Integer userId,String oldPassword, String newPassword)
	{
		if (checkPassword(oldPassword, userId) != null) {
			
			setPassword(newPassword,userId);
			
			return true;
			
		}
		return false;
	}
	
	public Account checkPassword(String password, Integer userId)
	{
		//Account account = accountMapper.getAccountByUserIdAndPwd(userId, password);
		Account account = accountMapper.getAccountByUserId(userId);
		
		if (account == null) {
			return null;
		}
		
		if (md5PasswordEncoder.encodePassword(password, account.getSalt()).equals(account.getPassword())) {
			
			return account;
		}
		
		
		return null;
	}
	
	public Integer addBankCard(Integer userId, String cardNo, String cardHolder, Integer bankId)
	{
		Account account = accountMapper.getAccountByUserId(userId);
		
		BankCard bankCard = new BankCard();
		bankCard.setAccountId(account.getId());
		bankCard.setBankId(bankId);
		bankCard.setCardNo(cardNo);
		bankCard.setCardHolder(cardHolder);
		
		try
		{
			Integer result = bankCardMapper.addBankCard(bankCard);
			
			return result;
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new AuthenticateException("插入失败");
			
		}
		
	}
	public List<BankCard> getBankCardList(Integer userId)
	{
		return bankCardMapper.getBankCardList(userId);
	}
	
	public Account getAccount(Integer userId)
	{
		return accountMapper.getAccountByUserId(userId);
	}
	
	public Integer deleteBankCard(Integer bankCardId)
	{
		return bankCardMapper.deleteBankCard(bankCardId);
	}
	
	public List<PayRecord> getPayRecord(Integer userId, Integer startIndex, Integer length)
	{
		return accountMapper.getPayRecord(userId, startIndex, length);
	}
	
	public TopUp addTopUp(Integer accountId, Integer mny)
	{
		TopUp topUp = new TopUp();
		topUp.setAccountId(accountId);
		topUp.setMny(mny);
		
		topUpMapper.addTopUp(topUp);
		
		return topUp;
	}
	
	public Withdraw addWithdraw(Account account, Integer mny, Integer bankCardId)
	{
		Withdraw withdraw = new Withdraw();
		withdraw.setAccountId(account.getId());
		withdraw.setMny(mny);
		withdraw.setBankCardId(bankCardId);
		
		withdrawMapper.addWithdraw(withdraw);
		
		addAccountTransaction(6, mny, account, withdraw.getId());
		
		return withdraw;
		
		
		
	}
	public List<Withdraw> getWithdrawList(Integer status, Integer startIndex, Integer length, Date startTime, Date endTime)
	{
		return withdrawMapper.getWithdrawList(status, startIndex, length, startTime, endTime);
	}
	
	public Integer getWithdrawListCount(Integer status,Date startTime, Date endTime)
	{
		return withdrawMapper.getWithdrawListCount(status, startTime, endTime);
	}
	
	public List<Account> getAccountList(String searchText, Integer startIndex, Integer length)
	{
		return accountMapper.getAccountList(searchText, startIndex, length);
	}
	public Integer getAccountListCount(String searchText) {
		return accountMapper.getAccountListCount(searchText);
	}
	
	public List<AccountTransaction> getTransactionByAccountId(Integer accountId) {
		
		return accountTransactionMapper.getTransactionByAccountId(accountId);
		
	}
	public List<AccountTransaction> getTransactionByAccountIdForApp(Integer accountId, Integer startIndex, Integer length) {
		
		return accountTransactionMapper.getTransactionByAccountIdForApp(accountId,startIndex,length);
		
	}
	
	public BankCard getBankCardInfoByCardNo(Integer accountId, String bankCardNo) {
		
		return bankCardMapper.getBankCardInfoByCardNo(accountId, bankCardNo);
		
	}
	
	public Integer addAccountTransaction(Integer transactionType, Integer transactionMny, Account account, Integer businessId)
	{
		/*1-学员报名 2-网络教育付费账号 3-先付后学 4-先学后付 5-钱包充值 6-钱包提款 7-招生奖励*/
		AccountTransaction accountTransaction = new AccountTransaction();
		accountTransaction.setTransactionType(transactionType);
		accountTransaction.setTransactionMny(transactionMny);
		accountTransaction.setBusinessId(businessId);
		
		if (account == null) {
			accountTransaction.setDesc("还没有开账户");
			accountTransaction.setAccountId(-1);
		
		}
		else
		{
			accountTransaction.setAccountId(account.getId());
			if (transactionMny < 0) {
				accountTransaction.setDesc("支付金额必须大于0");
				accountTransaction.setIsValid(0);
			}
			else if (isConsume(transactionType) && account.getMny() < transactionMny) {
				accountTransaction.setDesc("账户余额不足");
				accountTransaction.setIsValid(0);
			}
			else if (transactionType != 1 && transactionType != 2 && transactionType != 3 && transactionType != 4 && transactionType != 5 && transactionType != 6 && transactionType != 7) {
				accountTransaction.setDesc("无此种交易类型");
				accountTransaction.setIsValid(0);
			}
			else {
				accountTransaction.setIsValid(1);
			}
			//accountTransaction.setAccountMny(account.getMny());
			
			if (accountTransaction.getIsValid() == 1) {
				if (transactionType == 5 || transactionType == 7) {
					account.setMny(account.getMny() + transactionMny);
				}
				else
				{
					account.setMny(account.getMny() - transactionMny);
				}
				accountMapper.updateAccount(account);
			}
			
			
		}
		
		accountTransaction.setAccountMny(account.getMny());
		return accountTransactionMapper.addTransaction(accountTransaction);
		
	}
	public boolean isConsume(Integer transactionType) {
		return transactionType == 1 || transactionType == 2 || transactionType == 3 || transactionType == 4 || transactionType == 6 ;
	}
	
	public Account getAccountByAccountId(Integer accountId) {
		
		return accountMapper.getAccountByAccountId(accountId);
		
	}
	
	public Integer updateWithdrawStatus(Integer withdrawId, Integer status)
	{
		return withdrawMapper.updateWithdrawStatus(withdrawId, status);
	}
	
	public List<TransactionMonthSum> getTransactionMonthSum(Integer accountId)
	{
		return accountTransactionMapper.getTransactionMonthSum(accountId);
	}
	
	

}
