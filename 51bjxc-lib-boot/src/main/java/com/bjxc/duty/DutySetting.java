package com.bjxc.duty;

import java.util.ArrayList;
import java.util.List;

public class DutySetting {
	private List<DayDuty> cycle = new ArrayList<DayDuty>();

	public List<DayDuty> getCycle() {
		return cycle;
	}

	public void setCycle(List<DayDuty> cycle) {
		this.cycle = cycle;
	}
	
}
