package com.orionsolwings.osbiz.company.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orionsolwings.osbiz.company.model.CompanyProfile;
import com.orionsolwings.osbiz.company.service.CompanyProfileService;
import com.orionsolwings.osbiz.util.ApiResponses;

@RestController
@RequestMapping("/api/v1/company-profiles")
public class CompanyProfileController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyProfileController.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CompanyProfileService companyProfileService;

	@PostMapping("/createCompanyProfile")
	public ResponseEntity<ApiResponses<CompanyProfile>> createCompanyProfile(
			@RequestBody CompanyProfile companyProfile) {
		try {
			logger.info("Received Company Profile: {}", objectMapper.writeValueAsString(companyProfile));
			CompanyProfile saved = companyProfileService.createCompanyProfile(companyProfile);
			logger.info("Company profile saved with ID: {}", saved.getId());

			ApiResponses<CompanyProfile> response = new ApiResponses<>("Company profile saved successfully.", saved);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			logger.error("Error while saving company profile", e);

			ApiResponses<CompanyProfile> errorResponse = new ApiResponses<>("Failed to save company profile.", null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	// ✅ READ ALL
	@GetMapping("/getAllCompanyProfiles")
	public ResponseEntity<List<CompanyProfile>> getAllCompanyProfiles() {
		List<CompanyProfile> profiles = companyProfileService.getAllCompanyProfiles();
		logger.info("Retrieved {} company profiles", profiles.size());
		return ResponseEntity.ok(profiles);
	}

	// ✅ READ BY ID
	@GetMapping("/getCompanyProfileById/{id}")
	public ResponseEntity<?> getCompanyProfileById(@PathVariable String id) {
		Optional<CompanyProfile> profile = companyProfileService.getCompanyProfileById(id);
		if (profile.isPresent()) {
			logger.info("Found company profile with ID: {}", id);
			return ResponseEntity.ok(profile.get());
		} else {
			logger.warn("Company profile not found with ID: {}", id);
			return ResponseEntity.status(404).body("Company profile not found.");
		}
	}

	// ✅ UPDATE
	@PutMapping("/updateCompanyProfileById/{id}")
	public ResponseEntity<?> updateCompanyProfile(@PathVariable String id, @RequestBody CompanyProfile updatedProfile) {
		CompanyProfile updated = companyProfileService.updateCompanyProfile(id, updatedProfile);
		if (updated != null) {
			logger.info("Updated company profile with ID: {}", id);
			return ResponseEntity.ok("Company profile updated successfully.");
		} else {
			logger.warn("Company profile not found for update, ID: {}", id);
			return ResponseEntity.status(404).body("Company profile not found.");
		}
	}

	// ✅ DELETE
	@DeleteMapping("/deleteCompanyProfileById/{id}")
	public ResponseEntity<String> deleteCompanyProfile(@PathVariable String id) {
		boolean deleted = companyProfileService.deleteCompanyProfile(id);
		if (deleted) {
			logger.info("Deleted company profile with ID: {}", id);
			return ResponseEntity.ok("Company profile deleted successfully.");
		} else {
			logger.warn("Company profile not found for delete, ID: {}", id);
			return ResponseEntity.status(404).body("Company profile not found.");
		}
	}
}
