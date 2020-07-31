/*
 * Copyright 2020 University at Buffalo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.PocketCare.pocketCare.DAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
import com.mongodb.client.result.UpdateResult;

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

	public UserData getUserDataByUserName(String userName){
		return userDataRepository.findByUserName(userName);
	}

	public UserData getUserDataByDeviceId(String deviceId){
		return userDataRepository.findByDeviceId(deviceId);
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

	public UpdateResult updateUserDataTokenAndExpiry(String deviceId, String token, Long expiryTime) {
		Query query = new Query();
		query.addCriteria(Criteria.where("deviceId").is(deviceId));
		Update update = new Update();
		update.set("authToken", token);
		update.set("expiryTime", expiryTime);
		return mongoTemplate.upsert(query, update, UserData.class);
	}

	public UpdateResult updateAdminDataUserNameAndPassword(String deviceId, String userName, String password) {
		Query query = new Query();
		query.addCriteria(Criteria.where("deviceId").is(deviceId));
		Update update = new Update();
		update.set("userName", userName);
		update.set("password", password);
		update.set("isAdmin",true);
		return mongoTemplate.upsert(query, update, UserData.class);
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
