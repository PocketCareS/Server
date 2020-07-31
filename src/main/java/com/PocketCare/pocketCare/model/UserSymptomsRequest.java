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

public class UserSymptomsRequest {
	
	private long date;
	private List<String> usersSymptoms;
	private List<String> roommatesSymptoms;
	private List<String> othersSymptoms;

	public List<String> getRoommatesSymptoms() {
		return roommatesSymptoms;
	}

	public void setRoommatesSymptoms(List<String> roommatesSymptoms) {
		this.roommatesSymptoms = roommatesSymptoms;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public List<String> getUsersSymptoms() {
		return usersSymptoms;
	}

	public void setUsersSymptoms(List<String> usersSymptoms) {
		this.usersSymptoms = usersSymptoms;
	}

	public List<String> getOthersSymptoms() {
		return othersSymptoms;
	}

	public void setOthersSymptoms(List<String> othersSymptoms) {
		this.othersSymptoms = othersSymptoms;
	}

}
