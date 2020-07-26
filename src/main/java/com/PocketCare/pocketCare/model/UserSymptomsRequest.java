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
