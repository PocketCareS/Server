/*
 * Copyright 2020 University at Buffalo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
