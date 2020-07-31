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
