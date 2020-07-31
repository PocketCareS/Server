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

import com.PocketCare.pocketCare.Exception.AuthorizationException;
import com.PocketCare.pocketCare.Exception.ClientException;
import com.PocketCare.pocketCare.Exception.CustomException;
import com.PocketCare.pocketCare.Service.AdminService;
import com.PocketCare.pocketCare.Service.HealthAnalytics;
import com.PocketCare.pocketCare.Service.IBMNotificationService;
import com.PocketCare.pocketCare.Service.UserContactService;
import com.PocketCare.pocketCare.model.AdminDTO;
import com.PocketCare.pocketCare.model.DailyContactRequest;
import com.PocketCare.pocketCare.model.LoginDTO;
import com.PocketCare.pocketCare.model.NotifyDevicesRequest;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/analytics")
public class AnalyticsController {

    private static final Logger logger = LogManager.getLogger(AnalyticsController.class);


    @Autowired
    UserContactService userContactService;
    
    @Autowired
    HealthAnalytics healthAnalytics;
    
    
    @Autowired
    IBMNotificationService notificationService;
    
    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/contactData", method = RequestMethod.GET)
    public ResponseEntity<?> getContactDataAnalytics(@RequestHeader("token") String token, @RequestParam(name = "startDate") Long startDate,
                                                     @RequestParam(name = "endDate") Long endDate,
                                                     @RequestParam(name = "contactType", required = false) String contactType) {
        try {
            return new ResponseEntity<>(userContactService.getContactInfo(token, startDate,endDate,contactType), HttpStatus.OK);
        } catch (AuthorizationException ex) {
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

    @RequestMapping(value = "/contactDataAll", method = RequestMethod.GET)
    public ResponseEntity<?> getContactDataAnalyticsAggregated( @RequestParam(name = "startDate") Long startDate,
                                                     @RequestParam(name = "endDate") Long endDate,
                                                     @RequestParam(name = "contactType", required = false) String contactType,
                                                               @RequestParam(name = "graphType")String graphType) {
        try {
            return new ResponseEntity<>(userContactService.getContactInfoAggregated( startDate,endDate,contactType,graphType), HttpStatus.OK);
        } catch (AuthorizationException ex) {
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
    
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResponseEntity<?> getHealthAnalytics( @RequestParam(name = "startDate") Long startDate,
                                                     @RequestParam(name = "endDate") Long endDate) {
        try {
            return new ResponseEntity<>(healthAnalytics.getSummary(startDate, endDate), HttpStatus.OK);
        } catch (AuthorizationException ex) {
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
    
    @RequestMapping(value = "/contactTracing",method = RequestMethod.GET)
	public ResponseEntity<?> getContactTracing(@RequestParam(name = "deviceId") String deviceId) throws IOException {
		try{
			return new ResponseEntity<>(userContactService.getContactTracingList(deviceId),HttpStatus.OK);
		}
		catch (Exception ex){
			logger.error("Exception", ex);
			return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> isAdmin(@RequestBody LoginDTO loginDTO) {
        try {
            return new ResponseEntity<>(adminService.login(loginDTO.getUserName(), loginDTO.getPassword()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/createAdmin", method = RequestMethod.POST)
    public ResponseEntity<?> createAdmin(@RequestBody AdminDTO adminDTO) {
        try {
            return new ResponseEntity<>(adminService.createAdmin(adminDTO.getUserName(), adminDTO.getPassword(),
                    adminDTO.getDeviceId()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = "/tracing/notify",method = RequestMethod.POST)
    public ResponseEntity<?> saveContactDatav2(@RequestHeader("token") String token, @RequestBody NotifyDevicesRequest devices) {
		try{
			return new ResponseEntity<>(notificationService.sendNotifcation(devices.getDeviceIds()), HttpStatus.OK);
		}
		catch (Exception ex){
			logger.error("Exception", ex);
			return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
    
 
}
