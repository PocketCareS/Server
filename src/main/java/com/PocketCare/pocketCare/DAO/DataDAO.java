package com.PocketCare.pocketCare.DAO;

import org.springframework.stereotype.Component;

import com.PocketCare.pocketCare.Entities.ContactListData;
import com.mongodb.client.result.UpdateResult;

@Component
public interface DataDAO {

	public UpdateResult saveContact(ContactListData data);

	

}
