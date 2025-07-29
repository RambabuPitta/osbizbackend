package com.orionsolwings.osbiz.businesspartner.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.orionsolwings.osbiz.businesspartner.model.BusinessPartner;

@Repository
public interface BusinessPartnerRepository extends MongoRepository<BusinessPartner, String> {
    
    // Custom finder method using bpuid (not _id)
    BusinessPartner findByBpuid(String bpuid);
    
    List<BusinessPartner> findByBusinessCode(String businessCode);

}
