package com.orionsolwings.osbiz.userManagement.repository;

import com.orionsolwings.osbiz.userManagement.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserManagementRepository extends MongoRepository<User, String> {
    
    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);

    User findByUserId(String userId);

    User findByEmailAndPassword(String email, String password);
}
