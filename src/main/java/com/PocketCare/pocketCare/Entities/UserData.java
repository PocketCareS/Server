package com.PocketCare.pocketCare.Entities;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "UserData")
public class UserData {
	
    @Id
    private String deviceId;
    private String authToken;
    private List<String> vbtName = new ArrayList<>();
    private Long expiryTime;
    private String status;
    private String zipCode;      //Making it string as codes can contain special char like '-' or chars for some countries
    private String gender;
    private String ageGroup;
    private String ethnicGroup;
    private List<String> healthConditions;
    private Map<Long, String> dailyKeys;
    private String affiliation;
    private String visaStatus;
    private String userName;
    private String password;
    private boolean isAdmin;
    
	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getVisaStatus() {
		return visaStatus;
	}

	public void setVisaStatus(String visaStatus) {
		this.visaStatus = visaStatus;
	}

	public Map<Long, String> getDailyKeys() {
		return dailyKeys;
	}

	public void setDailyKeys(Map<Long, String> dailyKeys) {
		this.dailyKeys = dailyKeys;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<String> getHealthConditions() {
		return healthConditions;
	}

	public void setHealthConditions(List<String> healthConditions) {
		this.healthConditions = healthConditions;
	}

	public Long getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Long expiryTime) {
		this.expiryTime = expiryTime;
	}

	public void setVbtName(List<String> vbtName) {
		this.vbtName = vbtName;
	}

	public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public List<String> getVbtName() {
		return vbtName;
	}

	public void setVbtName(String vbtName) {
    	this.vbtName.add(vbtName);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
