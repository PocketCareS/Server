package com.PocketCare.pocketCare.model;

import java.util.List;

public class UserInformationRequest {
	
	private String gender;
	private List<String> healthCondition;
	private String zipCode;
	private String ageGroup;
	private String ethnicGroup;
	private String visaStatus;
	private String affiliation;
	
	public String getVisaStatus() {
		return visaStatus;
	}
	public void setVisaStatus(String visaStatus) {
		this.visaStatus = visaStatus;
	}
	public String getAffiliation() {
		return affiliation;
	}
	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}
	public String getEthnicGroup() {
		return ethnicGroup;
	}
	public void setEthnicGroup(String ethnicGroup) {
		this.ethnicGroup = ethnicGroup;
	}
	public String getAgeGroup() {
		return ageGroup;
	}
	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public List<String> getHealthCondition() {
		return healthCondition;
	}
	public void setHealthCondition(List<String> healthCondition) {
		this.healthCondition = healthCondition;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}
