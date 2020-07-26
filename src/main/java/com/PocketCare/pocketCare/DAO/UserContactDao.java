package com.PocketCare.pocketCare.DAO;

import com.PocketCare.pocketCare.Entities.EmbeddedContactListId;
import com.PocketCare.pocketCare.Entities.UserContactList;
import com.mongodb.client.result.UpdateResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserContactDao {
    public UserContactList addOrUpdate(UserContactList userContactList);
    public UpdateResult addOrUpdateContactList(List<UserContactList> userContactList, String deviceId);
    public List<UserContactList> getLastNDaysUserContactData(Integer days,String deviceId);
	UserContactList getContactDataById(EmbeddedContactListId id);
	List<UserContactList> getContactDataBydeviceIdAndDate(String deviceId,Long date);
	List<UserContactList> getContactDataByDate(Long date);
	List<UserContactList> getContactDataBydeviceIdAndDateAndHour(String deviceId,Long date,Long hour);
	UpdateResult updateUnResolvedInfo(EmbeddedContactListId id, int count, int duration);

    }
