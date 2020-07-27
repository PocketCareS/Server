package com.PocketCare.pocketCare.model;

import java.util.List;

public class NotifyDevicesRequest {
	private List<String> deviceIds;

	public List<String> getDeviceIds() {
		return deviceIds;
	}

	public void setDeviceIds(List<String> deviceIds) {
		this.deviceIds = deviceIds;
	}

}
