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

package com.PocketCare.pocketCare.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PocketCare.pocketCare.Constants.AppConstants;
import com.PocketCare.pocketCare.DAO.UserDataDAO;
import com.PocketCare.pocketCare.DAO.UserDataRepository;
import com.PocketCare.pocketCare.Entities.UserData;
import com.PocketCare.pocketCare.Exception.AuthorizationException;
import com.PocketCare.pocketCare.Exception.ClientException;
import com.PocketCare.pocketCare.Exception.CustomException;
import com.PocketCare.pocketCare.Utils.AppUtils;
import com.PocketCare.pocketCare.model.UserInformationRequest;
import com.PocketCare.pocketCare.model.UserInformationResponse;
import com.PocketCare.pocketCare.model.UserRegistrationResponse;

@Service
public class UserService {
	@Autowired
	UserDataDAO userDataDAO;

	@Autowired
	UserDataRepository userDataRepository;

	private static final Logger logger = LogManager.getLogger(UserService.class);

	public UserRegistrationResponse createUserData(UserData userData) throws CustomException {

		if (userData == null || userData.getDeviceId() == null || userData.getDeviceId().isEmpty()) {

			logger.debug("createUserData: ClientException: Either UserData or DeviceId is set to null");
			throw new ClientException();
		}

		logger.debug("createUserData: Inside function: DeviceId: " + userData.getDeviceId());
		String deviceId = userData.getDeviceId();
		UserData fetchedUserData = checkIfDeviceIdExists(deviceId);

		if (fetchedUserData != null) {
			// TODO make sure whatever the new parameters added should be set here too. Or
			// refactor the code.
			logger.debug("createUserData: DeviceId already exists. Fetching VBTName from database");
			updateExpiryTimeAndSave(fetchedUserData);
			UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
			userRegistrationResponse.setToken(fetchedUserData.getAuthToken());

			return userRegistrationResponse;
		}

		userData = generateTokenAndSetExpiryTime(userData);
		return userDataDAO.createUserData(userData);
	}

	public Collection<UserData> getUserData() {
		logger.debug("getUserData: Retrieving user data");
		return userDataDAO.getUserData();
	}

	private String getUniqueVbtName() {

		logger.debug("getUniqueVbtName: Generating VBTName");
		String randString = AppUtils. getRandomString(AppConstants.RANDSTRINGLEN);
		String vbtName = AppConstants.VBTPREFIX.concat(randString);
		if (isUnique(vbtName)) {
			logger.debug("getUniqueVbtName: Generated VBTName");
			return vbtName;
		} else {
			logger.debug("getUniqueVbtName: VBTName generated is not unique. Regenerating.");
			return getUniqueVbtName();
		}
	}

	private boolean isUnique(String vbtName) {
		logger.debug("isUnique: Checking if VBTName: " + vbtName + " is unique");
		return userDataRepository.findByVbtName(vbtName) == null;
	}

	private UserData checkIfDeviceIdExists(String deviceId) {
		logger.debug("checkIfDeviceIdExists: Checking if DeviceId exists in database");
		return userDataRepository.findByDeviceId(deviceId);
	}

	private UserData getVbtNameForDeviceId(String deviceId) {
		logger.debug("getVbtNameForDeviceId: Fetching VBTName for DeviceId from the database");
		UserData userData = userDataDAO.getVbtNameByDeviceId(deviceId);
		logger.debug(
				"getVbtNameForDeviceId: VBTName returned for DeviceId");
		return userData;
	}

	public UserRegistrationResponse setTokenAndGetUserDetails(String deviceId) {
		UserData userData = this.getVbtNameForDeviceId(deviceId);
		updateExpiryTimeAndSave(userData);
		UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
		userRegistrationResponse.setToken(userData.getAuthToken());
		return userRegistrationResponse;
	}

	public void updateExpiryTimeAndSave(UserData userData) {
		logger.debug("updateExpiryTime: Updating expiry time for user with deviceId");
		userData = this.generateTokenAndSetExpiryTime(userData);
		logger.debug("updateExpiryTime: Returning userData after setting expiry time");
		saveUser(userData);
	}

	public void saveUser(UserData userData) {
		logger.debug("saveUserData: save/update userData in db");
		userDataRepository.save(userData);
	}

	public UserData getUserFromToken(String token) {
		// TODO Auto-generated method stub
		logger.debug("getUserFromToken: Retrieving user from token");
		return userDataDAO.getUserByToken(token);
	}

	public UserData generateTokenAndSetExpiryTime(UserData userData) {
		logger.debug("generateTokenAndSetExpiryTime: Generating token and setting expiry for user with deviceId");
		String token = AppUtils.getRandomString(AppConstants.TOKENSTRINGLENGTH);
		Date date = new Date();
		Long longDate = (date.getTime() + TimeUnit.MINUTES.toMillis(AppConstants.TOKENEXPIRYTIME));
		userData.setAuthToken(token);
		userData.setExpiryTime(longDate);
//		userData = userDataRepository.insert(userData);
		logger.debug(
				"generateTokenAndSetExpiryTime: Returning token for user with deviceId");
		return userData;
	}

	public UserData verifyToken(String token) {
		logger.debug("verifyToken: Fetching user details from database from token");
		UserData user = getUserFromToken(token);
		Date date = new Date();
		if (user != null && user.getExpiryTime() > date.getTime()) {
			logger.debug("verifyToken: Token successfully verified. Returning user.");
			return user;
		} else {
			logger.debug("verifyToken: Token could not be verified. Returning null.");
			return null;
		}
	}

	public UserInformationResponse addOrUpdateUserInformation(UserInformationRequest userInformation, String token)
			throws CustomException {
		UserData user = verifyToken(token);
		if (user == null) {
			throw new AuthorizationException();
		}
		logger.debug("saveLocation: Token Verified successfully. Saving Location. ");
		if (userInformation.getGender() != null && !userInformation.getGender().isEmpty()) {
			user.setGender(userInformation.getGender());
		}
		if (userInformation.getZipCode() != null && !userInformation.getZipCode().isEmpty()) {
			user.setZipCode(userInformation.getZipCode());
		}
		if (userInformation.getHealthCondition() != null) {
			user.setHealthConditions(userInformation.getHealthCondition());
		}
		if (userInformation.getAgeGroup() != null && !userInformation.getAgeGroup().isEmpty()) {
			user.setAgeGroup(userInformation.getAgeGroup());
		}
		if (userInformation.getEthnicGroup() != null && !userInformation.getEthnicGroup().isEmpty()) {
			user.setEthnicGroup(userInformation.getEthnicGroup());
		}
		if (userInformation.getAffiliation() != null && !userInformation.getAffiliation().isEmpty()) {
			user.setAffiliation(userInformation.getAffiliation());
		}
		if (userInformation.getVisaStatus() != null && !userInformation.getVisaStatus().isEmpty()) {
			user.setVisaStatus(userInformation.getVisaStatus());
		}

		saveUser(user);
		return new UserInformationResponse(AppConstants.UPLOADSUCCESS, AppConstants.SUCCESS);
	}
}
