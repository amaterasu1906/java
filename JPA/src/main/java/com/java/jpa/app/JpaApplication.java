package com.java.jpa.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.java.jpa.app.services.IUploadService;

@SpringBootApplication
public class JpaApplication implements CommandLineRunner{

	@Autowired
	IUploadService service;
	
	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		service.deleteAll();
		service.init();
	}

}
