package com.PocketCare.pocketCare.DAO;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.PocketCare.pocketCare.Entities.UserData;

public interface UserDataRepository extends MongoRepository<UserData, Integer> {

	UserData findByVbtName(String vbtName);

	UserData findByDeviceId(String deviceId);

	UserData findByUserName(String userName);
	
}
