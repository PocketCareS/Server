package com.PocketCare.pocketCare.Entities;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.PocketCare.pocketCare.model.Coordinates;

@Document(collection = "LocationData")
public class LocationData {
	
	@Id
	private String deviceId;
	private List<Coordinates> location;
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public List<Coordinates> getLocation() {
		return location;
	}
	public void setLocation(List<Coordinates> location) {
		this.location = location;
	}
	
}
