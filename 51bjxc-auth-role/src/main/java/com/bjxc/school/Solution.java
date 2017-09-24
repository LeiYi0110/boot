package com.bjxc.school;

public class Solution {
	
	private Integer id;
	private String solutionName;
	private Integer insCount;
	private String auths = "";
	
	private boolean isInitCoachAppSetting;
	private boolean isInitAppRecruitSetting;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSolutionName() {
		return solutionName;
	}
	public void setSolutionName(String solutionName) {
		this.solutionName = solutionName;
	}
	public Integer getInsCount() {
		return insCount;
	}
	public void setInsCount(Integer insCount) {
		this.insCount = insCount;
	}
	public String getAuths() {
		return auths;
	}
	public void setAuths(String auths) {
		this.auths = auths;
	}
	public boolean isInitCoachAppSetting() {
		return isInitCoachAppSetting;
	}
	public void setInitCoachAppSetting(boolean isInitCoachAppSetting) {
		this.isInitCoachAppSetting = isInitCoachAppSetting;
	}
	public boolean isInitAppRecruitSetting() {
		return isInitAppRecruitSetting;
	}
	public void setInitAppRecruitSetting(boolean isInitAppRecruitSetting) {
		this.isInitAppRecruitSetting = isInitAppRecruitSetting;
	}
	
	

}
