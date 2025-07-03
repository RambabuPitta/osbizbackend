package com.orionsolwings.osbiz.businessPartner.repository;

import com.orionsolwings.osbiz.businessPartner.model.BusinessPartner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessPartnerRepository extends MongoRepository<BusinessPartner, String> {
    
    // Custom finder method using bpuid (not _id)
    BusinessPartner findByBpuid(String bpuid);
}
