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
}
