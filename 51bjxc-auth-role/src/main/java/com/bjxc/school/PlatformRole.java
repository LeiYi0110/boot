package com.bjxc.school;






import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class PlatformRole {
	
	private Integer id;
	private String roleName;
	private Integer roleUserCount;
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss") 
	private Date updateTime;
	private String operateUserName;
	
	private Integer operatorId;
	private String roleKey;
	
	private Integer insId;
	private String insCode;
	
	
	
	//private Integer 
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getRoleUserCount() {
		return roleUserCount;
	}
	public void setRoleUserCount(Integer roleUserCount) {
		this.roleUserCount = roleUserCount;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getOperateUserName() {
		return operateUserName;
	}
	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public String getRoleKey() {
		return roleKey;
	}
	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}
	public Integer getInsId() {
		return insId;
	}
	public void setInsId(Integer insId) {
		this.insId = insId;
	}
	
	public String getInsCode() {
		return insCode;
	}
	public void setInsCode(String insCode) {
		this.insCode = insCode;
	}
	
	

	
	
	
	

}
