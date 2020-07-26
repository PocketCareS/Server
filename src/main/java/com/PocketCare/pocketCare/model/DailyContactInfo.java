package com.PocketCare.pocketCare.model;

import java.util.List;
import java.util.Map;

public class DailyContactInfo {
	
	private String dailyKey;
	private Map<Long, Map<String, List<ContactVbtInfo>>> hourlyContactInfo;

	public Map<Long, Map<String, List<ContactVbtInfo>>> getHourlyContactInfo() {
		return hourlyContactInfo;
	}

	public void setHourlyContactInfo(Map<Long, Map<String, List<ContactVbtInfo>>> hourlyContactInfo) {
		this.hourlyContactInfo = hourlyContactInfo;
	}

	public String getDailyKey() {
		return dailyKey;
	}

	public void setDailyKey(String dailyKey) {
		this.dailyKey = dailyKey;
	}
	
	

}
