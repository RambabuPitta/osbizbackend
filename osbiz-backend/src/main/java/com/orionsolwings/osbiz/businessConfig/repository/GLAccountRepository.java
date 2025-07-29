package com.orionsolwings.osbiz.businessConfig.repository;

import com.orionsolwings.osbiz.businessConfig.model.GLAccount;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GLAccountRepository extends MongoRepository<GLAccount, String> {

	Optional<GLAccount> findByGlAccount(String glAccount);
    // Optional: custom finder methods
}