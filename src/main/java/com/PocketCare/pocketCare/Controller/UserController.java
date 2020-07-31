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

package com.PocketCare.pocketCare.Controller;

import com.PocketCare.pocketCare.Service.UserContactService;
import com.PocketCare.pocketCare.Utils.AppUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.PocketCare.pocketCare.Entities.UserData;
import com.PocketCare.pocketCare.Exception.AuthorizationException;
import com.PocketCare.pocketCare.Exception.ClientException;
import com.PocketCare.pocketCare.Exception.CustomException;
import com.PocketCare.pocketCare.Service.NotificationService;
import com.PocketCare.pocketCare.Service.UserService;
import com.PocketCare.pocketCare.Service.UserSymptomsService;

import com.PocketCare.pocketCare.model.UserInformationRequest;
import com.PocketCare.pocketCare.model.UserRegistrationRequest;
import com.PocketCare.pocketCare.model.UserSymptomsRequest;

import java.io.IOException;


@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@Autowired
	NotificationService notify;
	
	
	@Autowired
	UserSymptomsService userSymptomsService;

	@Autowired
	UserContactService userContactService;

	//@RequestMapping(value = "/notify", method = RequestMethod.GET)
	public ResponseEntity<?> tempfireNotification() {
		//TODO it's a temporary controller to test push notification
		//firebase.firebaseInit();
		logger.debug("Inside try");
		return ResponseEntity.ok(notify.sendType1Notification());
	}

	@PostMapping
	public ResponseEntity<?> addUserData(@RequestBody UserRegistrationRequest userRegistrationRequest) {

		UserData userData = new UserData();
		userData.setDeviceId(userRegistrationRequest.getDeviceId());
		logger.info("Inside user data controller");
		try {
			logger.debug("Inside try");
			return ResponseEntity.ok(userService.createUserData(userData));
		} catch (AuthorizationException ex) {
			logger.error("Inside catch unauth");
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (ClientException ex) {
			logger.error("Inside catch bad");
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CustomException ex) {
			// TODO Auto-generated catch block
			logger.error("Inside catch custom");
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			logger.error(ex);
			return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public ResponseEntity<?> addOrUpdateUserInfo(@RequestHeader("token") String token, @RequestBody UserInformationRequest userInformation) {
		try {
			logger.debug("addOrUpdateUserInfo Controller");
			return new ResponseEntity<>(userService.addOrUpdateUserInformation(userInformation, token), HttpStatus.OK);
		}  catch (AuthorizationException ex) {
			logger.error("Exception auth");
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (ClientException ex) {
			logger.error("Exception client", ex);
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CustomException ex) {
			logger.error("Exception custom", ex);
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}catch (Exception ex) {
			logger.error("Exception", ex);
			return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/symptoms", method = RequestMethod.POST)
	public ResponseEntity<?> addOrUpdateUserSymptoms(@RequestHeader("token") String token, @RequestBody UserSymptomsRequest userSymptomsRequest) {
		try {
			logger.debug("addOrUpdateUserSymptoms Controller");
			return new ResponseEntity<>(userSymptomsService.addOrUpdate(token, userSymptomsRequest), HttpStatus.OK);
		}  catch (AuthorizationException ex) {
			logger.error("Exception auth");
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (ClientException ex) {
			logger.error("Exception client", ex);
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CustomException ex) {
			logger.error("Exception custom", ex);
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}catch (Exception ex) {
			logger.error("Exception", ex);
			return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/sample", method = RequestMethod.GET)
	public ResponseEntity sampleAPI() {
		try {
			logger.debug("addOrUpdateUserSymptoms Controller");
			return new ResponseEntity<>("Hello World", HttpStatus.OK);
		}catch (Exception ex) {
			logger.error("Exception", ex);
			return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//@RequestMapping(value = "/getVBT",method = RequestMethod.GET)
	public ResponseEntity<?> getVbt(@RequestParam(name = "dailyKey")String dailyKey,
									@RequestParam(name = "hour") Long hour){
		String vbt = AppUtils.getVbtName(dailyKey,hour);
		return new ResponseEntity<>(vbt, HttpStatus.OK);
	}
	
}
