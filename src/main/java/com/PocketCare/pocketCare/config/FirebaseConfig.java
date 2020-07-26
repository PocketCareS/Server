package com.PocketCare.pocketCare.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.PocketCare.pocketCare.Controller.DataController;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FirebaseConfig {

	private static final Logger logger = LogManager.getLogger(DataController.class);

	// TODO .setCredentials(GoogleCredentials.getApplicationDefault())
	// If environment variable is set GOOGLE_APPLICATION_CREDENTIALS with json file
	// location
	public void firebaseInit() {

		FileInputStream serviceAccount;
		FirebaseOptions options = null;
		try {
			serviceAccount = new FileInputStream(
					"google-json/pocketcare-1889b-firebase-adminsdk-ujcd1-4d23f62f87.json");
			options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://pocketcare-1889b.firebaseio.com").build();
		} catch (FileNotFoundException e) {
			logger.error("Exception Firebase", e);
		} catch (IOException e) {
			logger.error("Exception Firebase", e);
		}

//		if(FirebaseApp.getInstance(FirebaseApp.DEFAULT_APP_NAME) != null) {
//		    return;
//		}
		try {
			FirebaseApp.getInstance(FirebaseApp.DEFAULT_APP_NAME);
		} catch (IllegalStateException e) {
			FirebaseApp.initializeApp(options);
		}

	}
}