package com.PocketCare.pocketCare.DAO;

import java.util.*;

import com.mongodb.client.result.UpdateResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.PocketCare.pocketCare.Entities.UserData;
import com.PocketCare.pocketCare.model.UserRegistrationResponse;

@Component
public class UserDataDAO {

	private static final Logger logger = LogManager.getLogger(UserDataDAO.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private UserDataRepository userDataRepository;

	public Collection<UserData> getUserData() {
		return userDataRepository.findAll();
	}

	public Optional<UserData> getUserDataById(int id) {
		return userDataRepository.findById(id);
	}

	// userData cannot be null checked in service
	public UserRegistrationResponse createUserData(UserData userData) {
		logger.debug("User Create", "inside createUserData");
		UserData responseData = userDataRepository.insert(userData);
		UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
		List<String> vbtNames = responseData.getVbtName();
		userRegistrationResponse.setToken(responseData.getAuthToken());
		logger.debug("User Create", "returning");
		return userRegistrationResponse;
	}

	public UserData getVbtNameByDeviceId(String deviceId) {
		UserData userData = userDataRepository.findByDeviceId(deviceId);
		return userData;
	}

	public UserData getUserByToken(String token) {
		logger.debug("User Data token ", "inside getUserByToken");
		Query query = new Query();
		query.addCriteria(Criteria.where("authToken").is(token));
		logger.debug("User Data token ", "querying with token");
		return mongoTemplate.findOne(query, UserData.class);
	}

	public List<String> convertVBTNameToDeviceId(List<String> contactList) {
		logger.debug("User VBT to DevceiId ", "inside convertVBTNameToDeviceId");
		Query query = new Query();
		List<String> deviceIds = new ArrayList<>();
		query.addCriteria(Criteria.where("vbtName").in(contactList));
		List<UserData> userData = mongoTemplate.find(query, UserData.class);
		if (userData != null && !userData.isEmpty()) {
			for (UserData retrievedUserData : userData) {
				deviceIds.add(retrievedUserData.getDeviceId());
			}
		}
		logger.debug("User VBT to DevceiId ", "returning deviceId convertVBTNameToDeviceId");
		return deviceIds;
	}

	public UpdateResult updateDailyKey(String deviceId, Long date, String dailyKey) {
		UserData userData = userDataRepository.findByDeviceId(deviceId);
		Map<Long, String> dailyKeys = userData.getDailyKeys();
		if (dailyKeys == null) {
			dailyKeys = new HashMap<>();
		}
		dailyKeys.put(date, dailyKey);
		Query query = new Query();
		query.addCriteria(Criteria.where("deviceId").is(deviceId));
		Update update = new Update();
		update.set("dailyKeys", dailyKeys);
		return mongoTemplate.upsert(query, update, UserData.class);

	}

	/**
	 * Only for testing
	 */
	@Deprecated
	public List<String> getAllFcmId() {
//		Query query = new Query(Criteria.where("fcmId").exists(true));
//		query.fields().include("fcmId");
//		List<UserData> userData = mongoTemplate.find(query, UserData.class);
//		List<String> fcms = new ArrayList<>();
//		for (UserData ele : userData) {
//			fcms.add(ele.getFcmId());
//		}
//		return fcms;
		return null;
	}

	public String getDailyKeyByDate(String deviceId, long date) {
		UserData user = userDataRepository.findByDeviceId(deviceId);
		if (user == null) {
			return null;
		}
		if (user.getDailyKeys() != null && !user.getDailyKeys().isEmpty() && user.getDailyKeys().containsKey(date)) {
			return user.getDailyKeys().get(date);
		}
		return null;
	}
	
	public long getUserDataCount() {
		List<UserData> all = mongoTemplate.findAll(UserData.class);
		if(Objects.isNull(all)) {
			return 0;
		}else {
			return all.size();
		}
		
	}

}
