package com.orionsolwings.osbiz.client.repository;

import com.orionsolwings.osbiz.client.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {

	boolean existsByPhoneNumber(String phoneNumber);

	boolean existsByEmailAddress(String phoneNumber);

	// Optional login support
	Client findByEmailAddressAndPassword(String emailAddress, String password);

	Client findByEmailAddress(String emailAddress);
}
