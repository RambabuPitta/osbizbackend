package com.orionsolwings.osbiz.company.repository;

import com.orionsolwings.osbiz.company.model.CompanyProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyProfileRepository extends MongoRepository<CompanyProfile, String> {

    boolean existsByEmailAddress(String emailAddress);

    boolean existsByCompanyName(String companyName);
}
