package com.bjxc.school;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class AccountTransaction {
	
	private Integer id;
	private Integer accountMny;
	private Integer transactionMny;
	private Date  transactionDate;
	private Integer transactionType;
	
	private String transactionMnyWithSign;
	
	private Integer businessId;
	private Integer accountId;
	
	private Integer isValid;
	
	private String desc;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAccountMny() {
		return accountMny;
	}
	public void setAccountMny(Integer accountMny) {
		this.accountMny = accountMny;
	}
	public Integer getTransactionMny() {
		return transactionMny;
	}
	public void setTransactionMny(Integer transactionMny) {
		this.transactionMny = transactionMny;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Integer getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(Integer transactionType) {
		this.transactionType = transactionType;
	}
	public Integer getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getTransactionMnyWithSign() {
		return transactionMnyWithSign;
	}
	public void setTransactionMnyWithSign(String transactionMnyWithSign) {
		this.transactionMnyWithSign = transactionMnyWithSign;
	}
	
	
	
}
