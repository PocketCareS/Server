package com.PocketCare.pocketCare.model;

import java.util.Map;

public class HealthAnalyticsResponse {
	
	Map<Long, HealthAnalyticsInfo> dateWiseHealthAnalytics;

	public Map<Long, HealthAnalyticsInfo> getDateWiseHealthAnalytics() {
		return dateWiseHealthAnalytics;
	}

	public void setDateWiseHealthAnalytics(Map<Long, HealthAnalyticsInfo> dateWiseHealthAnalytics) {
		this.dateWiseHealthAnalytics = dateWiseHealthAnalytics;
	}
}
