package com.orionsolwings.osbiz.businessPartner.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orionsolwings.osbiz.businessPartner.model.BusinessPartner;
import com.orionsolwings.osbiz.businessPartner.service.BusinessPartnerService;

@RestController
@RequestMapping("/api/v1/businessPartners")
public class BusinessPartnerController {

	private static final Logger logger = LoggerFactory.getLogger(BusinessPartnerController.class);

	@Autowired
	private BusinessPartnerService businessPartnerService;

	@Autowired
	private ObjectMapper objectMapper;

	@PostMapping("/createBusinessPartner")
	public ResponseEntity<BusinessPartner> createBusinessPartner(@RequestBody BusinessPartner partner) {
		try {
			logger.info("Received createBusinessPartner request: {}", objectMapper.writeValueAsString(partner));
		} catch (JsonProcessingException e) {
			logger.error("Error serializing create request", e);
		}

		BusinessPartner created = businessPartnerService.createBusinessPartner(partner);
		return ResponseEntity.ok(created);
	}

	@GetMapping("/listOfBusinessPartners")
	public ResponseEntity<List<BusinessPartner>> listBusinessPartners() {
		logger.info("Fetching all business partners (listBusinessPartners)");
		return ResponseEntity.ok(businessPartnerService.getAllBusinessPartners());
	}

	@GetMapping("/getBusinessPartner")
	public ResponseEntity<BusinessPartner> getBusinessPartner(@RequestParam String bpuid) {
		logger.info("Fetching business partner with bpuid: {}", bpuid);

		BusinessPartner partner = businessPartnerService.getBusinessPartnerByBpuid(bpuid);
		if (partner == null) {
			logger.warn("No business partner found for bpuid: {}", bpuid);
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(partner);
	}

	@PutMapping("/updateBusinessPartner")
	public ResponseEntity<?> updateBusinessPartner(@RequestBody Map<String, Object> requestData) {
		try {
			logger.info("Received updateBusinessPartner request: {}", objectMapper.writeValueAsString(requestData));
		} catch (JsonProcessingException e) {
			logger.error("Error serializing update request", e);
		}

		if (!requestData.containsKey("bpuid")) {
			logger.warn("Missing 'bpuid' in update request");
			return ResponseEntity.badRequest().body("Missing 'bpuid' in request");
		}

		String bpuid = (String) requestData.get("bpuid");

		try {
			BusinessPartner updated = businessPartnerService.updateBusinessPartnerByFields(bpuid, requestData);
			return ResponseEntity.ok(updated);
		} catch (RuntimeException e) {
			logger.warn("Update failed for bpuid: {}", bpuid, e);
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/deleteBusinessPartner")
	public ResponseEntity<?> deleteBusinessPartner(@RequestBody Map<String, Object> requestData) {
		try {
			logger.info("Received deleteBusinessPartner request: {}", objectMapper.writeValueAsString(requestData));
		} catch (JsonProcessingException e) {
			logger.error("Error serializing delete request", e);
		}

		if (!requestData.containsKey("bpuid")) {
			logger.warn("Missing 'bpuid' in delete request");
			return ResponseEntity.badRequest().body("Missing 'bpuid' in request");
		}

		String bpuid = (String) requestData.get("bpuid");

		try {
			businessPartnerService.deleteBusinessPartnerByBpuid(bpuid);
			return ResponseEntity.noContent().build();
		} catch (RuntimeException e) {
			logger.warn("Delete failed for bpuid: {}", bpuid, e);
			return ResponseEntity.notFound().build();
		}
	}
}
