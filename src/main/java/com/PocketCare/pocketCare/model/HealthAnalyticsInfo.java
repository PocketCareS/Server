package com.PocketCare.pocketCare.model;

import java.util.Map;

public class HealthAnalyticsInfo {
	private long filled;
	private long healthy;
	private long notHealthy;
	private long totalUsers;
	private Map<String, Integer> symptomsCount;
	
	
	
	public HealthAnalyticsInfo(long filled, long healthy, long notHealthy, long totalUsers,
			Map<String, Integer> symptomsCount) {
		super();
		this.filled = filled;
		this.healthy = healthy;
		this.notHealthy = notHealthy;
		this.totalUsers = totalUsers;
		this.symptomsCount = symptomsCount;
	}
	
	public long getFilled() {
		return filled;
	}
	public void setFilled(long filled) {
		this.filled = filled;
	}
	public long getHealthy() {
		return healthy;
	}
	public void setHealthy(long healthy) {
		this.healthy = healthy;
	}
	public long getNotHealthy() {
		return notHealthy;
	}
	public void setNotHealthy(long notHealthy) {
		this.notHealthy = notHealthy;
	}
	public long getTotalUsers() {
		return totalUsers;
	}
	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}
	public Map<String, Integer> getSymptomsCount() {
		return symptomsCount;
	}
	public void setSymptomsCount(Map<String, Integer> symptomsCount) {
		this.symptomsCount = symptomsCount;
	}
	
	
}
