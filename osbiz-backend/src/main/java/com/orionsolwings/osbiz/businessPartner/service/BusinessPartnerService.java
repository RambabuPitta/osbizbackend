package com.orionsolwings.osbiz.businessPartner.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orionsolwings.osbiz.businessPartner.dto.BusinessPartnerSummary;
import com.orionsolwings.osbiz.businessPartner.model.BusinessPartner;
import com.orionsolwings.osbiz.businessPartner.repository.BusinessPartnerRepository;

@Service
public class BusinessPartnerService {

	private static final Logger logger = LoggerFactory.getLogger(BusinessPartnerService.class);
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private BusinessPartnerRepository repository;

	public BusinessPartner createBusinessPartner(BusinessPartner partner) {
		logAsJson("Creating BusinessPartner", partner);
		BusinessPartner saved = repository.save(partner);
		logAsJson("Saved BusinessPartner", saved);
		return saved;
	}

	public Optional<BusinessPartner> getBusinessPartnerById(String id) {
		logger.info("Fetching BusinessPartner by Mongo ID: {}", id);
		Optional<BusinessPartner> result = repository.findById(id);
		result.ifPresent(partner -> logAsJson("Found BusinessPartner", partner));
		return result;
	}

	/**public List<BusinessPartner> getAllBusinessPartners() {
		logger.info("Fetching all BusinessPartners");
		List<BusinessPartner> list = repository.findAll();
		logAsJson("Total BusinessPartners Fetched", list);
		return list;
	}*/
	
	
	public List<BusinessPartnerSummary> getBusinessPartnerSummariesByBusinessCode(String businessCode) {
	    List<BusinessPartner> partners = repository.findByBusinessCode(businessCode);
	    List<BusinessPartnerSummary> summaries = new ArrayList<>();

	    for (BusinessPartner bp : partners) {
	        BusinessPartnerSummary summary = new BusinessPartnerSummary();
	        summary.setBusinessCode(bp.getBusinessCode());
	        summary.setBpuid(bp.getBpuid());
	        summary.setBusinessPartner(bp.getBusinessPartnerName());
	        summary.setEntitytype(bp.getEntityType());
	        summary.setEntityName(bp.getEntityName());
	        summary.setStatus(bp.getStatus());
	        summary.setGlAccount(bp.getGlAccount());
	        summary.setBillPolicy(bp.getBillPolicy());
	        summary.setEmailid(bp.getEmailAddress());
	        summary.setPhoneNumber(bp.getPhoneNumber());
	        summary.setPaymentType(bp.getPaymentType());

	        summaries.add(summary);
	    }

	    return summaries;
	}


	
	/**
	 * need to authentication, authorization check
	 * 
	 * 
	 * 
	 * @param id
	 * @param updatedPartner
	 * @return
	 */
	public BusinessPartner updateBusinessPartner(String id, BusinessPartner updatedPartner) {
	    logger.info("Attempting full update for Mongo ID: {}", id);

	    if (!repository.existsById(id)) {
	        logger.warn("BusinessPartner not found with ID: {}", id);
	        throw new RuntimeException("BusinessPartner not found with ID: " + id);
	    }

	    updatedPartner.setBpuid(id); // Ensure ID is retained
	    logAsJson("Updating with", updatedPartner);

	    BusinessPartner saved = repository.save(updatedPartner);
	    logAsJson("Updated BusinessPartner", saved);
	    return saved;
	}


	public void deleteBusinessPartner(String id) {
		logger.info("Deleting BusinessPartner by Mongo ID: {}", id);
		repository.deleteById(id);
	}

	public BusinessPartner getBusinessPartnerByBpuid(String bpuid) {
		logger.info("Fetching BusinessPartner by bpuid: {}", bpuid);
		BusinessPartner result = repository.findByBpuid(bpuid);
		if (result != null) {
			logAsJson("Found BusinessPartner", result);
		} else {
			logger.warn("No BusinessPartner found with bpuid: {}", bpuid);
		}
		return result;
	}

//	public BusinessPartner updateBusinessPartnerByFields(String bpuid, Map<String, Object> data) {
//		logger.info("Starting partial update for bpuid: {}", bpuid);
//		logAsJson("Update payload", data);
//
//		BusinessPartner existing = repository.findByBpuid(bpuid);
//		if (existing == null) {
//			logger.warn("BusinessPartner not found for bpuid: {}", bpuid);
//			throw new RuntimeException("BusinessPartner not found for bpuid: " + bpuid);
//		}
//
//		logAsJson("Before update", existing);
//
//		if (data.containsKey("entityName"))
//			existing.setEntityName((String) data.get("entityName"));
//		if (data.containsKey("businessPartner"))
//			existing.setBusinessPartnerName((String) data.get("businessPartner"));
//		if (data.containsKey("status"))
//			existing.setStatus((String) data.get("status"));
//		if (data.containsKey("billPolicy"))
//			existing.setBillPolicy((String) data.get("billPolicy"));
//		if (data.containsKey("emailid"))
//			existing.setEmailAddress((String) data.get("emailid"));
//		if (data.containsKey("phoneNumber"))
//			existing.setPhoneNumber((String) data.get("phoneNumber"));
//		if (data.containsKey("paymentType"))
//			existing.setPaymentType((String) data.get("paymentType"));
//		if (data.containsKey("glAccount"))
//			existing.setGlAccount((String) data.get("glAccount"));
//
//		BusinessPartner saved = repository.save(existing);
//
//		logAsJson("After update", saved);
//		return saved;
//	}

	public void deleteBusinessPartnerByBpuid(String bpuid) {
		logger.info("Attempting to delete BusinessPartner by bpuid: {}", bpuid);
		BusinessPartner existing = repository.findByBpuid(bpuid);
		if (existing == null) {
			logger.warn("BusinessPartner not found for bpuid: {}", bpuid);
			throw new RuntimeException("BusinessPartner not found for bpuid: " + bpuid);
		}
		repository.delete(existing);
		logger.info("Deleted BusinessPartner with bpuid: {}", bpuid);
	}
	
	
public BusinessPartner updateBusinessPartnerByFields(String bpuid, Map<String, Object> updates) {
	    BusinessPartner existing = repository.findByBpuid(bpuid);
	    if (existing == null) {
	        throw new RuntimeException("BusinessPartner not found with bpuid: " + bpuid);
	    }

	    // Ensure bpuid is not changed
	    updates.remove("bpuid");

	    // Merge updates into existing object
	    try {
			objectMapper.updateValue(existing, updates);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    // Save the updated document
	    return repository.save(existing);
	}


	private void logAsJson(String message, Object obj) {
		try {
			logger.info("{}: {}", message, objectMapper.writeValueAsString(obj));
		} catch (JsonProcessingException e) {
			logger.error("JSON serialization error for {}: {}", message, e.getMessage());
		}
	}
}
