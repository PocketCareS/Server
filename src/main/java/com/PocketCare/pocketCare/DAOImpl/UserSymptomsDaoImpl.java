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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.PocketCare.pocketCare.DAO.UserSymptomsDao;
import com.PocketCare.pocketCare.Entities.UserData;
import com.PocketCare.pocketCare.Entities.UserSymptons;
import com.mongodb.client.result.UpdateResult;

@Repository
public class UserSymptomsDaoImpl implements UserSymptomsDao {

	private static final Logger logger = LogManager.getLogger(UserSymptomsDaoImpl.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public UpdateResult addOrUpdate(UserSymptons symptoms) {
		// TODO Auto-generated method stub

		logger.debug("User symptoms", "inside addOrUpdate symptoms");
		Query query = new Query();
		query.addCriteria(Criteria.where("symptomsId").is(symptoms.getSymptomsId()));
		//query.addCriteria(Criteria.where("date").is(symptoms.getDate()));
		Update update = new Update();
		update.set("usersSymptoms", symptoms.getUsersSymptoms());
		update.set("othersSymptoms", symptoms.getOthersSymptoms());
		update.set("roommatesSymptoms", symptoms.getRoommatesSymptoms());
		logger.debug("User symptoms", "updating addOrUpdate symptoms");
		return mongoTemplate.upsert(query, update, UserSymptons.class);
	}
	
	@Override
	public long getHealthyCounts(long startDate, long endDate){
		Query query = new Query();
		List<Criteria> cr = new ArrayList<>();
		Criteria cr1 = new Criteria().orOperator(Criteria.where("usersSymptoms").is("I am feeling fine"),Criteria.where("usersSymptoms").is(Arrays.asList()), Criteria.where("usersSymptoms").is("true"));
		Criteria cr2 = new Criteria().andOperator(Criteria.where("symptomsId.date").gte(startDate),Criteria.where("symptomsId.date").lte(endDate));
		
		cr.add(cr1);
		cr.add(cr2);
		query.addCriteria(new Criteria().andOperator(cr.toArray(new Criteria[cr.size()])));
		long result = mongoTemplate.count(query, UserSymptons.class);
		return result;
	}
	
	@Override
	public long getHealthyCounts(long startDate){
		Query query = new Query();
		List<Criteria> cr = new ArrayList<>();
		Criteria cr1 = new Criteria().orOperator(Criteria.where("usersSymptoms").is("I am feeling fine"),Criteria.where("usersSymptoms").is(Arrays.asList()), Criteria.where("usersSymptoms").is("true"));
		Criteria cr2 = new Criteria().andOperator(Criteria.where("symptomsId.date").is(startDate));
		
		cr.add(cr1);
		cr.add(cr2);
		query.addCriteria(new Criteria().andOperator(cr.toArray(new Criteria[cr.size()])));
		long result = mongoTemplate.count(query, UserSymptons.class);
		return result;
	}
	
	@Override
	public List<UserSymptons> getUnHealthInfo(long startDate, long endDate){
		Query query = new Query();
		List<Criteria> cr = new ArrayList<>();
		Criteria cr1 = new Criteria().andOperator(Criteria.where("usersSymptoms").ne("I am feeling fine"),Criteria.where("usersSymptoms").ne(Arrays.asList()), Criteria.where("usersSymptoms").ne("true"));
		Criteria cr2 = new Criteria().andOperator(Criteria.where("symptomsId.date").is(startDate),Criteria.where("symptomsId.date").lte(endDate));
		cr.add(cr1);
		cr.add(cr2);
		query.addCriteria(new Criteria().andOperator(cr.toArray(new Criteria[cr.size()])));
		List<UserSymptons> result = mongoTemplate.find(query, UserSymptons.class);
		return result;
	}
	
	@Override
	public List<UserSymptons> getUnHealthInfo(long startDate){
		Query query = new Query();
		List<Criteria> cr = new ArrayList<>();
		Criteria cr1 = new Criteria().andOperator(Criteria.where("usersSymptoms").ne("I am feeling fine"),Criteria.where("usersSymptoms").ne(Arrays.asList()), Criteria.where("usersSymptoms").ne("true"));
		Criteria cr2 = new Criteria().andOperator(Criteria.where("symptomsId.date").is(startDate));
		cr.add(cr1);
		cr.add(cr2);
		query.addCriteria(new Criteria().andOperator(cr.toArray(new Criteria[cr.size()])));
		List<UserSymptons> result = mongoTemplate.find(query, UserSymptons.class);
		return result;
	}


	@Override
	public long getTotalFilled(long start, long end) {
		Query query = new Query();
		
		Criteria cr2 = new Criteria().andOperator(Criteria.where("symptomsId.date").gte(start),Criteria.where("symptomsId.date").lte(end));
		query.addCriteria(cr2);
		long result = mongoTemplate.count(query, UserSymptons.class);
		return result;
	}
	
	@Override
	public long getTotalFilled(long start) {
		Query query = new Query();
		
		Criteria cr = new Criteria().andOperator(Criteria.where("symptomsId.date").is(start));
		query.addCriteria(cr);
		long result = mongoTemplate.count(query, UserSymptons.class);
		return result;
	}

}
