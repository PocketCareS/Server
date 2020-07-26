package com.PocketCare.pocketCare.DAO;

import java.util.List;

import com.PocketCare.pocketCare.Entities.DailyContacts;
import com.PocketCare.pocketCare.Entities.EmbeddedContactsId;
import com.PocketCare.pocketCare.model.ContactVbtInfo;

public interface DailyContactDAO {
	public DailyContacts get(EmbeddedContactsId id);
	public DailyContacts addOrUpdate(EmbeddedContactsId id, String deviceId, List<ContactVbtInfo> contactVbtInfo);
	public List<ContactVbtInfo> getDeviceContactInfo(EmbeddedContactsId id, String deviceId);
	public DailyContacts getDailyContactsById(EmbeddedContactsId id);
	public List<DailyContacts> getDailyContactsByDate(Long date);

}
