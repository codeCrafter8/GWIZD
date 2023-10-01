package com.gwizd;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@SpringBootApplication
public class GwizdApplication {

	public static void main(String[] args) {
		String databaseUrl = "https://gwizd-d41f8-default-rtdb.firebaseio.com/";
		String storageBucket = "gwizd-d41f8.appspot.com";

		FirebaseOptions options = null;
		try {
			ClassPathResource serviceAccount = new ClassPathResource("firebase-service-account.json");
			options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
					.setDatabaseUrl(databaseUrl)
					.setStorageBucket(storageBucket)
					.build();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if(FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options, "gwizd_appname");
		}else {
			FirebaseApp.initializeApp(options);
		}
		SpringApplication.run(GwizdApplication.class, args);
	}

}
