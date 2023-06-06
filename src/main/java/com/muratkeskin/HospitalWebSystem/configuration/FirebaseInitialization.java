package com.muratkeskin.HospitalWebSystem.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class FirebaseInitialization {

    @PostConstruct
    public  void initialization() throws FileNotFoundException, IOException {
    FileInputStream serviceAccount =
            null;
    try{
            serviceAccount=new FileInputStream("./serviceAccountKey.json");
    FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

      FirebaseApp.initializeApp(options);
    }
    catch (Exception e){
        e.printStackTrace();
        //.setDatabaseUrl("https://blood-donation-app-bedf5-default-rtdb.firebaseio.com")
    }
    }

}
