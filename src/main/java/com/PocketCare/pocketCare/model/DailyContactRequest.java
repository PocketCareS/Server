package com.PocketCare.pocketCare.model;

import java.util.Map;

public class DailyContactRequest {
	private Map<Long, DailyContactInfo> dateWiseContactInfo;

	public Map<Long, DailyContactInfo> getDateWiseContactInfo() {
		return dateWiseContactInfo;
	}

	public void setDateWiseContactInfo(Map<Long, DailyContactInfo> dateWiseContactInfo) {
		this.dateWiseContactInfo = dateWiseContactInfo;
	}
    
}
