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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.PocketCare.pocketCare.DAO.DailyContactDAO;
import com.PocketCare.pocketCare.Entities.DailyContacts;
import com.PocketCare.pocketCare.Entities.EmbeddedContactsId;
import com.PocketCare.pocketCare.model.ContactVbtInfo;

@Repository
public class DailyContactsDAOImpl implements DailyContactDAO {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public DailyContacts get(EmbeddedContactsId id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("dailyContactsId").is(id));
		return mongoTemplate.findOne(query, DailyContacts.class);
		
	}

	// TODO: Testing: 1) Check for functionality when (date,hour,zip) key is created for the first time, and 2) When (date, hour,zip) key already exists
	// and we need to append data to it
	@Override
	public DailyContacts addOrUpdate(EmbeddedContactsId id, String deviceId, List<ContactVbtInfo> contactVbtInfo) {
		DailyContacts dailyContacts = getDailyContactsById(id);	// Check if this is null. If yes, then create an entry with this ID and return
		Map<String, List<ContactVbtInfo>> contactMapping = new HashMap<String, List<ContactVbtInfo>>();
		if(dailyContacts != null) {
			contactMapping = dailyContacts.getContactMapping();
			contactMapping.put(deviceId, contactVbtInfo);
		}
		else{
			dailyContacts = new DailyContacts();
			dailyContacts.setDailyContactsId(id);
			contactMapping.put(deviceId, contactVbtInfo);
		}
		dailyContacts.setContactMapping(contactMapping);
		return mongoTemplate.save(dailyContacts);
	}
	
	@Override
	public List<ContactVbtInfo> getDeviceContactInfo(EmbeddedContactsId id, String deviceId) {
		DailyContacts dailyContacts = getDailyContactsById(id);
		List<ContactVbtInfo> contactInfo = null;
		if(dailyContacts != null) {
			Map<String, List<ContactVbtInfo>> contactMapping = dailyContacts.getContactMapping();
			if(contactMapping !=null && contactMapping.size()>0) {
				contactInfo = contactMapping.get(deviceId);
			}
		}
		if(contactInfo == null) {
			contactInfo = new ArrayList<>();
		}
		return contactInfo;
		
	}
	
	@Override
	public DailyContacts getDailyContactsById(EmbeddedContactsId id) {
		return mongoTemplate.findById(id, DailyContacts.class);
	}

	@Override
	public List<DailyContacts> getDailyContactsByDate(Long date){
		Query query = new Query();
		query.addCriteria(Criteria.where("dailyContactsId.date").is(date));
		return mongoTemplate.find(query, DailyContacts.class);
	}



}
