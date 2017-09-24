package com.bjxc.duty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DayDuty {
	private Integer week;
	private Date  Day;
	private List<Integer> tims = new ArrayList<Integer>();
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}
	public Date getDay() {
		return Day;
	}
	public void setDay(Date day) {
		Day = day;
	}
	public List<Integer> getTims() {
		return tims;
	}
	public void setTims(List<Integer> tims) {
		this.tims = tims;
	}
	
	
}
