package com.bjxc.school;

import java.sql.Date;

public class TopUp {
	
	/*
	 *   `id` int(11) NOT NULL AUTO_INCREMENT,
  `mny` int(11) NOT NULL,
  `account_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0-未支付 1-已支付',
	 * */
	
	private Integer id;
	private Integer mny;
	private Integer accountId;
	private Date createTime;
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMny() {
		return mny;
	}
	public void setMny(Integer mny) {
		this.mny = mny;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
