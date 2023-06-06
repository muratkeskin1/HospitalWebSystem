package com.muratkeskin.HospitalWebSystem;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.muratkeskin.HospitalWebSystem.model.Hospital;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

@SpringBootApplication(scanBasePackages = {"com.muratkeskin"})
public class HospitalWebSystemApplication {
	public static void main(String[] args) throws IOException {
		/*
		String databaseUrl="https://blood-donation-app-bedf5-default-rtdb.firebaseio.com";
		String apiKey="";
		FileInputStream serviceAccount =
				new FileInputStream("./serviceAccountKey.json");
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();

		FirebaseApp.initializeApp(options);*/
		SpringApplication.run(HospitalWebSystemApplication.class, args);
	}


}
