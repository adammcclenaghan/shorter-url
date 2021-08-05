package com.example.accessingmongodbdatarest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccessingMongodbDataRestApplication {

	//TODO: Add something which prevents anything other than POST/GET calls
	public static void main(String[] args) {
		SpringApplication.run(AccessingMongodbDataRestApplication.class, args);
	}

}
