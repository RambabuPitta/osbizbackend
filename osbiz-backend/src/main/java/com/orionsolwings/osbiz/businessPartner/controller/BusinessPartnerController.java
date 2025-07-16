package com.orionsolwings.osbiz.businessPartner.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.orionsolwings.osbiz.businessPartner.dto.BusinessPartnerSummary;
import com.orionsolwings.osbiz.businessPartner.model.BusinessPartner;
import com.orionsolwings.osbiz.businessPartner.service.BusinessPartnerService;
import com.orionsolwings.osbiz.util.ApiResponses;

@RestController
@RequestMapping("/api/v1/businessPartners")
public class BusinessPartnerController {

	private static final Logger logger = LoggerFactory.getLogger(BusinessPartnerController.class);

	@Autowired
	private BusinessPartnerService businessPartnerService;

	@Autowired
	private ObjectMapper objectMapper;

	@PostMapping("/createBusinessPartner")
	public ResponseEntity<ApiResponses> createBusinessPartner(@RequestBody BusinessPartner partner) {
		try {
			logger.info("Received createBusinessPartner request: {}", objectMapper.writeValueAsString(partner));

			BusinessPartner created = businessPartnerService.createBusinessPartner(partner);

			if (created != null) {
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new ApiResponses("Business Partner created successfully", "Success"));
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ApiResponses("Failed to add Business Partner,Duplicate BPUID. A Business Partner with this ID already exists.", "Fail"));
			}

		} catch (JsonProcessingException e) {
			logger.error("Error serializing create request", e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponses("Invalid request data", "Fail"));

		} catch (Exception e) {
			logger.error("Unexpected error during creation", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponses("Something went wrong", "Fail"));
		}
	}

	@GetMapping("/listOfBusinessPartners")
	public ResponseEntity<List<BusinessPartnerSummary>> listBusinessPartners(@RequestParam String businessCode) {

		logger.info("Fetching business partners with businessCode: {}", businessCode);
		List<BusinessPartnerSummary> summaries = businessPartnerService
				.getBusinessPartnerSummariesByBusinessCode(businessCode);
		return ResponseEntity.ok(summaries);
	}

	@GetMapping("/getBusinessPartner")
	public ResponseEntity<ApiResponses<BusinessPartner>> getBusinessPartner(@RequestParam String bpuid) {
	    logger.info("Fetching business partner with bpuid: {}", bpuid);

	    BusinessPartner partner = businessPartnerService.getBusinessPartnerByBpuid(bpuid);
	    if (partner == null) {
	        logger.warn("No business partner found for bpuid: {}", bpuid);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(new ApiResponses<>("Failed to retrieve Business Partner details. Please try again later.", "Fail", null));
	    }

	    return ResponseEntity.ok(new ApiResponses<>("Business Partner fetched successfully", "Success", partner));
	}


//	@PutMapping("/updateBusinessPartner")
//	public ResponseEntity<ApiResponses> updateBusinessPartner(@RequestBody Map<String, Object> requestData) {
//		try {
//			logger.info("Received updateBusinessPartner request: {}", objectMapper.writeValueAsString(requestData));
//		} catch (JsonProcessingException e) {
//			logger.error("Error serializing update request", e);
//		}
//
//		if (!requestData.containsKey("bpuid")) {
//			logger.warn("Missing 'bpuid' in update request");
//			return ResponseEntity.badRequest().body(new ApiResponses("Missing 'bpuid' in request", "Fail"));
//		}
//
//		String bpuid = (String) requestData.get("bpuid");
//
//		try {
//			businessPartnerService.updateBusinessPartnerByFields(bpuid, requestData);
//			return ResponseEntity.ok(new ApiResponses("Business Partner updated successfully", "Success"));
//		} catch (RuntimeException e) {
//			logger.warn("Update failed for bpuid: {}", bpuid, e);
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new ApiResponses("Business Partner not found", "Fail"));
//		}
//	}

	@PostMapping("/updateBusinessPartner")
	public ResponseEntity<ApiResponses> updateBP(@RequestBody BusinessPartner requestData) {
		try {
			// Validate input
			if (requestData.getBpuid() == null || requestData.getBpuid().isEmpty()) {
				return ResponseEntity.badRequest().body(new ApiResponses("BPUID is required", "Fail"));
			}

			// Log incoming request
			logger.info("Received updateBusinessPartner request: {}", objectMapper.writeValueAsString(requestData));

			// Call service
			BusinessPartner bp = businessPartnerService.updateBusinessPartner(requestData.getBpuid(), requestData);

			if (bp != null) {
				return ResponseEntity.ok(new ApiResponses("BP Details Updated Successfully", "Success"));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ApiResponses("Business Partner not found", "Fail"));
			}

		} catch (JsonProcessingException e) {
			logger.error("Error serializing update request", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponses("Error processing request", "Fail"));

		} catch (Exception e) {
			logger.error("Unexpected error occurred during update", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponses("Something went wrong", "Fail"));
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

	// BusinessPartnerController.java

//	@PutMapping("/updateBusinessPartner")
//	public ResponseEntity<?> updateBusinessPartner(@RequestBody Map<String, Object> requestData) {
//	    try {
//	        logger.info("Received updateBusinessPartner request: {}", objectMapper.writeValueAsString(requestData));
//	    } catch (JsonProcessingException e) {
//	        logger.error("Error serializing update request", e);
//	    }
//
//	    if (!requestData.containsKey("bpuid")) {
//	        return ResponseEntity.badRequest().body("Missing 'bpuid' in request");
//	    }
//
//	    String bpuid = (String) requestData.get("bpuid");
//
//	    try {
//	        BusinessPartner updated = businessPartnerService.updateBusinessPartnerByFields(bpuid, requestData);
//	        return ResponseEntity.ok(updated);
//	    } catch (RuntimeException e) {
//	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//	    }
//	}

}
