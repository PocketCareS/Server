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

package com.PocketCare.pocketCare.DAOImpl;

import com.PocketCare.pocketCare.Constants.AppConstants;
import com.PocketCare.pocketCare.DAO.UserContactDao;
import com.PocketCare.pocketCare.Entities.ContactListData;
import com.PocketCare.pocketCare.Entities.EmbeddedContactListId;
import com.PocketCare.pocketCare.Entities.UserContactList;
import com.mongodb.client.result.UpdateResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class UserContactDaoImpl implements UserContactDao {

	private static final Logger logger = LogManager.getLogger(UserSymptomsDaoImpl.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public UserContactList addOrUpdate(UserContactList userContactList) {

		logger.debug("UserContactDaoImpl", "Inside AddOrUpdate");
//		Query query = new Query();
//		query.addCriteria(Criteria.where("contactListId").is(userContactList.getContactListId()));
//
//		Update update = new Update();
//		update.set("contactLists", userContactList.getContactLists());
//
//
		return mongoTemplate.save(userContactList);
	}

	@Override
	public List<UserContactList> getLastNDaysUserContactData(Integer days, String deviceId) {
		Query query = new Query();
		Date date = new Date();
		long date_late = date.getTime() - (long) days * AppConstants.NUMBER_OF_MILLIS_IN_A_DAY;
		query.addCriteria(
				Criteria.where("contactListId.date").gte(date_late).and("contactListId.deviceId").is(deviceId));
		return mongoTemplate.find(query, UserContactList.class);
	}

	@Deprecated
	@Override
	public UpdateResult addOrUpdateContactList(List<UserContactList> userContactLists, String deviceId) {
		List<String> contactList = new ArrayList<>();
		for (UserContactList userContactList : userContactLists) {
			contactList.addAll(userContactList.getContactLists());
		}
		Query query = new Query();
		query.addCriteria(Criteria.where("deviceId").is(deviceId));
		Update update = new Update();
		update.set("contactList", contactList);
		return mongoTemplate.upsert(query, update, ContactListData.class);
	}

	@Override
	public UserContactList getContactDataById(EmbeddedContactListId id) {
		return mongoTemplate.findById(id, UserContactList.class);
	}

	@Override
	public List<UserContactList> getContactDataBydeviceIdAndDate(String deviceId,Long date){
		Query query = new Query();
		query.addCriteria(Criteria.where("contactListId.date").is(date).and("contactListId.deviceId").is(deviceId));
		return mongoTemplate.find(query, UserContactList.class);
	}

	@Override
	public List<UserContactList> getContactDataByDate(Long date){
		Query query = new Query();
		query.addCriteria(Criteria.where("contactListId.date").is(date));
		return mongoTemplate.find(query, UserContactList.class);
	}

	@Override
	public List<UserContactList> getContactDataBydeviceIdAndDateAndHour(String deviceId, Long date, Long hour) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//TODO test it
	@Override
	public UpdateResult updateUnResolvedInfo(EmbeddedContactListId id, int count, int duration) {
		Query query = new Query();
		query.addCriteria(Criteria.where("contactListId").is(id));
		Update update = new Update();
		update.set("unResolvedCC", count);
		update.set("unResolvedCCDuration", duration);
		return mongoTemplate.upsert(query, update, UserContactList.class);
	}

}
