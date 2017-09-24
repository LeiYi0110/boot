package com.bjxc.school;

import java.util.List;

public class Auth {
	
	private Integer id;
	private String name;
	private Integer level;
	
	private Boolean checked = false;
	
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	private List<Auth> list;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Auth> getList() {
		return list;
	}
	public void setList(List<Auth> list) {
		this.list = list;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	
	

}
