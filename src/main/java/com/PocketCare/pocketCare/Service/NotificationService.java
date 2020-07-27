package com.PocketCare.pocketCare.Service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PocketCare.pocketCare.Constants.AppConstants;
import com.PocketCare.pocketCare.DAO.UserDataDAO;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;

@Service
public class NotificationService {

	private static final Logger logger = LogManager.getLogger(UserDataDAO.class);

	@Autowired
	UserDataDAO userDataDao;

	@Deprecated
	public List<String> sendType1Notification() {
		List<String> fcms = userDataDao.getAllFcmId();
		try {
			sendMulticastAndHandleErrors(fcms);
		} catch (FirebaseMessagingException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		return fcms;
	}

	public void sendType2Notification() {

	}

	@Deprecated
	public void sendMulticast(List<String> registrationTokens) throws FirebaseMessagingException {
		// [START send_multicast]
		// Create a list containing up to 100 registration tokens.
		// These registration tokens come from the client FCM SDKs.
//		List<String> registrationTokens = Arrays.asList("YOUR_REGISTRATION_TOKEN_1",
//				// ...
//				"YOUR_REGISTRATION_TOKEN_n");

		Notification notification = Notification.builder().setTitle(AppConstants.TYPE1TITLE)
				.setBody(AppConstants.TYPE1BODY).build();

		MulticastMessage message = MulticastMessage.builder().setNotification(notification)
				.putData(AppConstants.TYPE1DATAKEY, AppConstants.TYPE1DATAVAL).putData("time", "2:45")
				.addAllTokens(registrationTokens).build();
		BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
		// See the BatchResponse reference documentation
		// for the contents of response.
		logger.debug(response.getSuccessCount() + " messages were sent successfully");
		// [END send_multicast]
	}

	@Deprecated
	public void sendMulticastAndHandleErrors(List<String> registrationTokens) throws FirebaseMessagingException {
		// [START send_multicast_error]
		// These registration tokens come from the client FCM SDKs.
//		List<String> registrationTokens = Arrays.asList("YOUR_REGISTRATION_TOKEN_1",
//				// ...
//				"YOUR_REGISTRATION_TOKEN_n");

		Notification notification = Notification.builder().setTitle(AppConstants.TYPE1TITLE)
				.setBody(AppConstants.TYPE1BODY).build();

		MulticastMessage message = MulticastMessage.builder().setNotification(notification)
				.putData(AppConstants.TYPE1DATAKEY, AppConstants.TYPE1DATAVAL).putData("time", "2:45")
				.addAllTokens(registrationTokens).build();
		BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
		if (response.getFailureCount() > 0) {
			List<SendResponse> responses = response.getResponses();
			List<String> failedTokens = new ArrayList<>();
			for (int i = 0; i < responses.size(); i++) {
				if (!responses.get(i).isSuccessful()) {
					// The order of responses corresponds to the order of the registration tokens.
					failedTokens.add(registrationTokens.get(i));
				}
			}

			logger.debug("List of tokens that caused failures: " + failedTokens);
		}
		// [END send_multicast_error]
	}
	
	
	

}
