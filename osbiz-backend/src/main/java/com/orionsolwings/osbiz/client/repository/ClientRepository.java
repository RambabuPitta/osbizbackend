package com.orionsolwings.osbiz.client.repository;

import com.orionsolwings.osbiz.client.model.ClientModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<ClientModel, String> {
    boolean existsByPhoneNumber(String phoneNumber);

    // Optional login support
    ClientModel findByEmailAddressAndPassword(String emailAddress, String password);
    
    ClientModel findByEmailAddress(String emailAddress);
}
