package com.PocketCare.pocketCare.Controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.PocketCare.pocketCare.Exception.AuthorizationException;
import com.PocketCare.pocketCare.Exception.ClientException;
import com.PocketCare.pocketCare.Exception.CustomException;
import com.PocketCare.pocketCare.Service.DataService;
import com.PocketCare.pocketCare.Service.UserContactService;
import com.PocketCare.pocketCare.model.DailyContactRequest;
import com.PocketCare.pocketCare.model.DataUploadRequest;
import com.PocketCare.pocketCare.model.UserContactRequest;

@RestController
@RequestMapping("/upload")
public class DataController {

	private static final Logger logger = LogManager.getLogger(DataController.class);
	
	@Autowired
	DataService dataService;

	@Autowired
	UserContactService userContactService;

//	@RequestMapping(value = "/contactlist", method = RequestMethod.POST)
	@Deprecated
	public ResponseEntity<?> saveContactData(@RequestBody DataUploadRequest dataToUpload,
			@RequestHeader("token") String token, HttpServletResponse response) {

		try {
			logger.debug("Inside ContactList Data Controller");
			return new ResponseEntity<>(dataService.saveContact(token, dataToUpload), HttpStatus.OK);
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

	//@RequestMapping(value = "/contactlist", method = RequestMethod.POST)
	@Deprecated
	public ResponseEntity<?> saveContactData(@RequestHeader("token") String token, @RequestBody UserContactRequest userContactRequest) {
		try {
			return new ResponseEntity<>(userContactService.addOrUpdate(token, userContactRequest), HttpStatus.OK);
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
	
	@RequestMapping(value = "/contactlist", method = RequestMethod.POST)
	public ResponseEntity<?> saveContactDatav2(@RequestHeader("token") String token, @RequestBody DailyContactRequest userContactRequest) {
		try {
			return new ResponseEntity<>(userContactService.addOrUpdate(token, userContactRequest), HttpStatus.OK);
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

}
