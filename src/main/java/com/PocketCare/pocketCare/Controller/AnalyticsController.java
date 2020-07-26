package com.PocketCare.pocketCare.Controller;

import com.PocketCare.pocketCare.Exception.AuthorizationException;
import com.PocketCare.pocketCare.Exception.ClientException;
import com.PocketCare.pocketCare.Exception.CustomException;
import com.PocketCare.pocketCare.Service.HealthAnalytics;
import com.PocketCare.pocketCare.Service.UserContactService;

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

}
