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
					  "\"private_key\": \"-----BEGIN PRIVATE KEY-----\nMIIEvQIJSJDSDJSJDA,smds,mlslsldslmd,smBAQDasl/h7nwWTBzF\nR8duEwlW3OtUxVM5SVRF8iLQpflkE7WmtyeQRxfipy25HKTgEZhdIl0U6AnShiel\nRzLO6E6XY1S1UPN22O7cOlkgd0PtruG3EweZmMZMPIAgKsgLI+3EUZBLxdmSN2DQ\nXMwVr3AYvkigVw3A9DHFu2iAj1PgbNu2AZugtZVVp00+kHyXEbfPdJ22pxZofA6b\nT+mqOVkQUcscLABuVqoJZpGLkrgU+5IGjgF4G0O2KZdyjWWKji1Lqe/zNj+jI9Wk\n1BOJohJorHfolksjksncsiaoosz8747823\nSg3jJyU/AgMkKSKJSKksnw9qnm98CgnQiUBHrsB4oS7MCTVNBeohtoYzjgLo5aqJ\n5AUiC/4sVl7JWkVnVd2Br8weIQtgNvNAIVp+ZgMfFnzNoOXCAo9GtC+rkPaplVad\nO3VpGjZJrUdHsUx8X93RSUQP5Y/fcYo6Ir6OY4hvfTwU4cnlZXyvP0dESy1eu6OG\nwLrKo/U9WZm3+IySF8QhngaPKM05ucH7EikSOhDOW/BuoBfdH8R3MZYf6Oq9r02s\nWongppDpwC+77PgxPPRQuoQektwRkvBHJyDjTynLWQKBgQDyVu4BDyLOj/hMRz+6\n9nCrkh9dPNxa0FNruUDqdjhb9YMD2IpaMpej/SfsJ3BTUSzX+Bfhf3z60MzxRYau\nSH/9akgbgUu1aUQHheqMIZE+nq0oL0azg3blDjGuSYnJcjdhTGUTKacJt9RSSmlY\na0cck6R9gtKPqImqz9tj1Da3SQKBgQDnBkTAlVrJ92w4xpJ+TsDif3NOhdFtjWkB\nz//beTS9BhKH/wFlVxcwEbavC0iikw4dnMqN3wj2ySNS1OKcw02TdEWZx8z2gN4s\nUanwMeWsjsX5IsYsMEMOqTrekZHg0vQd/ZCSCjg85Ww6TrSE7zBgG75m519Mn+qa\nNkqMv5fQRwKBgG+rHPsB9YEHmFvVzQki/CIjMC6vl76FFpuh/sbLeQbu59NC2eXc\nQEIBqn7IYZsS4b4XBTIUpAuTYYygBAKVR90HN3jtKGzMt9tbmwu9gAdB6PqGZmbb\n4lKEKuJr4oEgD2LXaA/fdqgkvEK7JTXoIRmK8xYezVYDTISYSTkEFb55AoGBAJT6\nT9+48KEJMvqx4soc0vm8oojidpkk9bA7h4zqbn3uqoiUgzvsm4FeCuZ6ak6cch/a\neixexZMPRf4mVjQtJZuPAUsXsy7LD3qY9vNz2d4a+8ObDxxzyupraxEX4nCO2Ol2\nGIJ0oKt0bwRyuKp47EMt4OzPC3+IKGtl01FMRAQFAoGAH1huaSTb0ZXxEQ3W2QVF\nIR0pHD6RPFgZdR5IoRNyhQse6ayuriUag4/cVZnsnTbfsbd4SBoy7BQG9j8S4/67\nrdycrqwdhvvZe9pxcL2jvPqTi73UE9QoQI++7CKrzEzn5Ycnp9g++Z/GcsVOq7Cs\n02/O2mraJ1DcN6RvXhIjcHY=\n-----END PRIVATE KEY-----\n\","+
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
