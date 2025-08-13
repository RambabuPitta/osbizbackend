package com.orionsolwings.osbiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mongodb.client.MongoClient;

import jakarta.annotation.PreDestroy;

@SpringBootApplication
public class OsbizBackendApplication {

	@Autowired
	private MongoClient mongoClient;

	public static void main(String[] args) {
		SpringApplication.run(OsbizBackendApplication.class, args);
	}

	@PreDestroy
	public void closeMongoClient() {
		if (mongoClient != null) {
			mongoClient.close();
		}
	}

}
