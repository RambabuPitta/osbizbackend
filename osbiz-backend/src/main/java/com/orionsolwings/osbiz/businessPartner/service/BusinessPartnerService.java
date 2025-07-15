package com.orionsolwings.osbiz.businessPartner.service;

import java.util.List;
import java.util.Map;
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

	public List<BusinessPartner> getAllBusinessPartners() {
		logger.info("Fetching all BusinessPartners");
		List<BusinessPartner> list = repository.findAll();
		logAsJson("Total BusinessPartners Fetched", list);
		return list;
	}

	public BusinessPartner updateBusinessPartner(String id, BusinessPartner updatedPartner) {
		logger.info("Attempting full update for Mongo ID: {}", id);
		if (repository.existsById(id)) {
			updatedPartner.setBpuid(id); // Retain ID as bpuid
			logAsJson("Updating with", updatedPartner);
			BusinessPartner saved = repository.save(updatedPartner);
			logAsJson("Updated BusinessPartner", saved);
			return saved;
		} else {
			logger.warn("BusinessPartner not found with ID: {}", id);
			throw new RuntimeException("BusinessPartner not found with ID: " + id);
		}
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

	public BusinessPartner updateBusinessPartnerByFields(String bpuid, Map<String, Object> data) {
		logger.info("Starting partial update for bpuid: {}", bpuid);
		logAsJson("Update payload", data);

		BusinessPartner existing = repository.findByBpuid(bpuid);
		if (existing == null) {
			logger.warn("BusinessPartner not found for bpuid: {}", bpuid);
			throw new RuntimeException("BusinessPartner not found for bpuid: " + bpuid);
		}

		logAsJson("Before update", existing);

		if (data.containsKey("entityName"))
			existing.setEntityName((String) data.get("entityName"));
		if (data.containsKey("businessPartner"))
			existing.setBusinessPartnerName((String) data.get("businessPartner"));
		if (data.containsKey("status"))
			existing.setStatus((String) data.get("status"));
		if (data.containsKey("billPolicy"))
			existing.setBillPolicy((String) data.get("billPolicy"));
		if (data.containsKey("emailid"))
			existing.setEmailAddress((String) data.get("emailid"));
		if (data.containsKey("phoneNumber"))
			existing.setPhoneNumber((String) data.get("phoneNumber"));
		if (data.containsKey("paymentType"))
			existing.setPaymentType((String) data.get("paymentType"));
		if (data.containsKey("glAccount"))
			existing.setGlAccount((String) data.get("glAccount"));

		BusinessPartner saved = repository.save(existing);

		logAsJson("After update", saved);
		return saved;
	}

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

	private void logAsJson(String message, Object obj) {
		try {
			logger.info("{}: {}", message, objectMapper.writeValueAsString(obj));
		} catch (JsonProcessingException e) {
			logger.error("JSON serialization error for {}: {}", message, e.getMessage());
		}
	}
}
