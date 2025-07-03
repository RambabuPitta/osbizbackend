package com.orionsolwings.osbiz.businessPartner.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orionsolwings.osbiz.businessPartner.model.BusinessPartner;
import com.orionsolwings.osbiz.businessPartner.service.BusinessPartnerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/business-partners")
public class BusinessPartnerController {

	Logger logger=LoggerFactory.getLogger(BusinessPartnerController.class);
	
    @Autowired
    private BusinessPartnerService service;

    @Autowired
    private ObjectMapper objectMapper; // Automatically available if using Spring Boot

    // Create
    @PostMapping("/create")
    public ResponseEntity<BusinessPartner> create(@RequestBody BusinessPartner partner) {
        try {
        	logger.info("Creating business partner: {}", objectMapper.writeValueAsString(partner));
        } catch (JsonProcessingException e) {
        	logger.error("Error serializing request body", e);
        }

        BusinessPartner saved = service.createBusinessPartner(partner);
        return ResponseEntity.ok(saved);
    }

    // Get by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<BusinessPartner> getById(@PathVariable String id) {
    	logger.info("Fetching business partner with ID: {}", id);
        Optional<BusinessPartner> partner = service.getBusinessPartnerById(id);
        return partner.map(ResponseEntity::ok)
                      .orElseGet(() -> {
                    	  logger.warn("Business partner not found for ID: {}", id);
                          return ResponseEntity.notFound().build();
                      });
    }

    // Get all
    @GetMapping("/list")
    public ResponseEntity<List<BusinessPartner>> listAll() {
    	logger.info("Fetching all business partners");
        return ResponseEntity.ok(service.getAllBusinessPartners());
    }

    // Update
    @PutMapping("/update/{id}")
    public ResponseEntity<BusinessPartner> update(@PathVariable String id, @RequestBody BusinessPartner updated) {
        try {
        	logger.info("Updating business partner with ID: {} -> Data: {}", id, objectMapper.writeValueAsString(updated));
        } catch (JsonProcessingException e) {
        	logger.error("Error serializing update payload", e);
        }

        try {
            BusinessPartner updatedBP = service.updateBusinessPartner(id, updated);
            return ResponseEntity.ok(updatedBP);
        } catch (RuntimeException e) {
        	logger.warn("Update failed, business partner not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
    	logger.info("Deleting business partner with ID: {}", id);
        service.deleteBusinessPartner(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/getByBpuid/{bpuid}")
    public ResponseEntity<BusinessPartner> getByBpuid(@PathVariable String bpuid) {
        logger.info("Fetching business partner with bpuid: {}", bpuid);
        
        BusinessPartner partner = service.getBusinessPartnerByBpuid(bpuid);
        
        if (partner == null) {
            logger.warn("Business partner not found for bpuid: {}", bpuid);
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(partner);
    }

}
