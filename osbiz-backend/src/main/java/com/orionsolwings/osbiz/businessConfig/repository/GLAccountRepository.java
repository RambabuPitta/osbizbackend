package com.orionsolwings.osbiz.businessConfig.repository;

import com.orionsolwings.osbiz.businessConfig.model.GLAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GLAccountRepository extends MongoRepository<GLAccount, String> {
    // Optional: custom finder methods
}