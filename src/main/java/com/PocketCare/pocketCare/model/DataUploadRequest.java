package com.PocketCare.pocketCare.model;

import java.util.List;

public class DataUploadRequest {
	private List<String> contactList;
	private List<Coordinates> location;

	public List<String> getContactList() {
		return contactList;
	}

	public void setContactList(List<String> contactList) {
		this.contactList = contactList;
	}

	public List<Coordinates> getLocation() {
		return location;
	}

	public void setLocation(List<Coordinates> location) {
		this.location = location;
	}
}
