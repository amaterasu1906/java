package com.java.jpa.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.java.jpa.app.services.IUploadService;

@SpringBootApplication
public class JpaApplication implements CommandLineRunner{

	@Autowired
	IUploadService service;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		service.deleteAll();
		service.init();
		
		String password = "sa";
		
		for (int i = 0; i < 2; i++) {
			String pBcryp = passwordEncoder.encode(password);
			System.out.println(pBcryp);
		}
	}

}
