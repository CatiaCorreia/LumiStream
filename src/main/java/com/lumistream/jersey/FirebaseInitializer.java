package com.lumistream.jersey;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;

public class FirebaseInitializer {
    public static void initializeFirebase() {
        try {
            // Path to the Firebase service account key
          //  FileInputStream serviceAccount = new FileInputStream("/home/fanineto1/LumiStream/config/lumistream-fani-firebase-adminsdk-p89ku-a5a22091a4.json");
            FileInputStream serviceAccount = new FileInputStream("/opt/LumiStream/config/lumistream-fani-firebase-adminsdk-p89ku-a5a22091a4.json");

            // Configure Firebase options
            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("lumistream-fani.firebasestorage.app") 
                .build();

            // Initialize Firebase
            FirebaseApp.initializeApp(options);
            System.out.println("Firebase initialized successfully!");
        } catch (Exception e) {
            System.err.println("Failed to initialize Firebase: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
