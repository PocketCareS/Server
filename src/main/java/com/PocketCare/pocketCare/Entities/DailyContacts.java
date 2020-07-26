package com.PocketCare.pocketCare.Entities;

import java.util.List;
import java.util.Map;

import javax.persistence.EmbeddedId;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.PocketCare.pocketCare.model.ContactVbtInfo;

@Document(collection = "DailyContacts")
public class DailyContacts {

	@Id
	@EmbeddedId
	private EmbeddedContactsId dailyContactsId;

	Map<String, List<ContactVbtInfo>> contactMapping;
	
	public EmbeddedContactsId getDailyContactsId() {
		return dailyContactsId;
	}

	public void setDailyContactsId(EmbeddedContactsId dailyContactsId) {
		this.dailyContactsId = dailyContactsId;
	}

	public Map<String, List<ContactVbtInfo>> getContactMapping() {
		return contactMapping;
	}

	public void setContactMapping(Map<String, List<ContactVbtInfo>> contactMapping) {
		this.contactMapping = contactMapping;
	}
	
	

}
