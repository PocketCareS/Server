package com.PocketCare.pocketCare.DAO;

import java.util.List;

import com.mongodb.client.result.UpdateResult;
import org.springframework.stereotype.Component;

import com.PocketCare.pocketCare.Entities.ContactListData;
import com.PocketCare.pocketCare.Entities.LocationData;
import com.PocketCare.pocketCare.model.Coordinates;
import com.mongodb.client.result.UpdateResult;

@Component
public interface DataDAO {

	public UpdateResult saveContact(ContactListData data);

	public UpdateResult saveLocation(String deviceId, List<Coordinates> data);

}
