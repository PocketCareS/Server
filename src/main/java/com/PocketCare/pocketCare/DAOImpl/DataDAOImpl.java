package com.PocketCare.pocketCare.DAOImpl;

import java.util.List;

import com.mongodb.client.result.UpdateResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.PocketCare.pocketCare.DAO.DataDAO;
import com.PocketCare.pocketCare.DAO.UserDataDAO;
import com.PocketCare.pocketCare.Entities.ContactListData;
import com.PocketCare.pocketCare.Entities.LocationData;
import com.PocketCare.pocketCare.model.Coordinates;
import com.mongodb.client.result.UpdateResult;

@Repository
public class DataDAOImpl implements DataDAO {

	private static final Logger logger = LogManager.getLogger(UserDataDAO.class);
	
	@Autowired
	private MongoTemplate mongoTemplate;


	@Override
	public UpdateResult saveContact(ContactListData data) {
		logger.debug("Data save Contact", "inside saveContact");
		Query query = new Query();
		query.addCriteria(Criteria.where("deviceId").is(data.getDeviceId()));
		Update update = new Update();
		update.set("contactList", data.getContactList());
		logger.debug("Data save Contact", "updating saveContact");
		return mongoTemplate.upsert(query, update, ContactListData.class);
	}

	@Override
	public UpdateResult saveLocation(String deviceId, List<Coordinates> data) {
		logger.debug("Data save location", "inside saveLocation");
		Query query = new Query();
        query.addCriteria(Criteria.where("deviceId").is(deviceId));
        Update update = new Update();
        update.set("location", data);
        logger.debug("Data save location", "updating location");
        return mongoTemplate.upsert(query,update,LocationData.class);
	}
}
