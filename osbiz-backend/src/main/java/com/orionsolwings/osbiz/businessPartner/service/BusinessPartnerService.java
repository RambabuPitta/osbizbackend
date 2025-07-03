package com.orionsolwings.osbiz.businessPartner.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orionsolwings.osbiz.businessPartner.model.BusinessPartner;
import com.orionsolwings.osbiz.businessPartner.repository.BusinessPartnerRepository;

@Service
public class BusinessPartnerService {
	
	Logger logger=LoggerFactory.getLogger(BusinessPartnerService.class);
	ObjectMapper mapper=new ObjectMapper();

    @Autowired
    private BusinessPartnerRepository repository;

    // Create
    public BusinessPartner createBusinessPartner(BusinessPartner partner) {
        return repository.save(partner);
    }

    // Get by ID
    public Optional<BusinessPartner> getBusinessPartnerById(String id) {
    	
//    	try {
//			logger.info(mapper.writeValueAsString(repository.findById(id)));
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	
        return repository.findById(id);
    }

    // Get all
    public List<BusinessPartner> getAllBusinessPartners() {
        return repository.findAll();
    }

    // Update
    public BusinessPartner updateBusinessPartner(String id, BusinessPartner updatedPartner) {
        if (repository.existsById(id)) {
            updatedPartner.setBpuid(id); // Retain the same ID
            return repository.save(updatedPartner);
        } else {
            throw new RuntimeException("Business partner not found with ID: " + id);
        }
    }

    // Delete
    public void deleteBusinessPartner(String id) {
        repository.deleteById(id);
    }
    
    public BusinessPartner getBusinessPartnerByBpuid(String bpuid) {
        return repository.findByBpuid(bpuid);
    }


    // Optional: Extra actions
//    public Optional<BusinessPartner> getByBusinessCode(String code) {
//        return repository.findByBusinessCode(code);
//    }
//
//    public boolean existsByPhoneNumber(String phoneNumber) {
//        return repository.existsByPhoneNumber(phoneNumber);
//    }
}
