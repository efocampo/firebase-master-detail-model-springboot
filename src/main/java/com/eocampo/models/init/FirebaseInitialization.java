package com.eocampo.models.init;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;





@Service
public class FirebaseInitialization {
	
	@SuppressWarnings("unused")
	@PostConstruct
	public void initialization() {
		try {
			
			String con =
					"{ " +
					  "\"type\": \"service_account\"," +
					  "\"project_id\": \"YOUR_PROJECT_ID\","+
					  "\"private_key_id\": \"YOUR_PRIVATE_KEY_ID\","+
					  "\"private_key\": \"-----BEGIN PRIVATE KEY-----\n/4IySaJ1DcN6RvXhIjcHY=\n-----END PRIVATE KEY-----\n\","+
					  "\"client_email\": \"YOUR_CLIENT_EMAIL\","+
					  "\"client_id\": \"YOUR_CLIENT_ID\","+
					  "\"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\","+
					  "\"token_uri\": \"https://oauth2.googleapis.com/token\","+
					  "\"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\","+
					  "\"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-1szc1%40proy-petcare.iam.gserviceaccount.com\""+
					"}";
			
			InputStream is = IOUtils.toInputStream(con, StandardCharsets.UTF_8);
			
			
		
			FirebaseApp finestayApp;
			
			FirebaseOptions options = FirebaseOptions.builder() 
					  .setCredentials(GoogleCredentials.fromStream(is)) 
					  .build(); 
			
			boolean hasBeenInitialized=false;
			List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
			for(FirebaseApp app : firebaseApps){
			    if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)){
			        hasBeenInitialized=true;
			        finestayApp = app;
			    }
			}
			
			if(!hasBeenInitialized) {
			    finestayApp = FirebaseApp.initializeApp(options);
			} 
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
