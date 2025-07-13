package com.orionsolwings.osbiz.company.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orionsolwings.osbiz.company.model.CompanyProfile;
import com.orionsolwings.osbiz.company.repository.CompanyProfileRepository;
import com.orionsolwings.osbiz.userManagement.service.UserManagementService;
import com.orionsolwings.osbiz.util.EmailService;

@Service
public class CompanyProfileService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyProfileService.class);

    private final CompanyProfileRepository repository;
    private final ObjectMapper objectMapper;
    
    @Autowired
    UserManagementService userManagementService;
    
    @Autowired
    EmailService emailService;

    @Autowired
    public CompanyProfileService(CompanyProfileRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

 // ✅ Create
    public CompanyProfile createCompanyProfile(CompanyProfile companyProfile) {
        try {
            logger.info("Received request to create Company Profile: {}", objectMapper.writeValueAsString(companyProfile));

            CompanyProfile savedProfile = repository.save(companyProfile);
            logger.info("Company Profile saved successfully with ID: {}", savedProfile.getId());

            if (savedProfile != null) {
                logger.info("Creating admin user and assigning permissions based on company profile...");
                String msg = userManagementService.createAdminUserFromCompanyProfile(savedProfile);
                logger.info("Admin user and permissions created for company: {}", savedProfile.getCompanyName());
                logger.info(msg);

                // Send email with company profile details (excluding password)
                emailService.sendCompanyProfileEmail(savedProfile);
                logger.info("Company profile email sent to: {}", savedProfile.getEmailAddress());
            }

            return savedProfile;

        } catch (Exception e) {
            logger.error("Error occurred while creating Company Profile", e);
            throw new RuntimeException("Failed to create Company Profile", e);
        }
    }



    // ✅ Read All
    public List<CompanyProfile> getAllCompanyProfiles() {
        List<CompanyProfile> profiles = repository.findAll();
        logger.info("Fetched {} Company Profiles", profiles.size());
        return profiles;
    }

    // ✅ Read by ID
    public Optional<CompanyProfile> getCompanyProfileById(String id) {
        logger.info("Fetching Company Profile by ID: {}", id);
        return repository.findById(id);
    }

    // ✅ Update
    public CompanyProfile updateCompanyProfile(String id, CompanyProfile updatedProfile) {
        logger.info("Attempting to update Company Profile with ID: {}", id);
        return repository.findById(id).map(existing -> {
            updatedProfile.setId(id);
            try {
                logger.info("Updated Company Profile data: {}", objectMapper.writeValueAsString(updatedProfile));
            } catch (Exception e) {
                logger.warn("Could not convert updated profile to JSON for logging", e);
            }
            CompanyProfile saved = repository.save(updatedProfile);
            logger.info("Company Profile updated with ID: {}", saved.getId());
            return saved;
        }).orElse(null);
    }

    // ✅ Delete
    public boolean deleteCompanyProfile(String id) {
        logger.info("Attempting to delete Company Profile with ID: {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            logger.info("Deleted Company Profile with ID: {}", id);
            return true;
        } else {
            logger.warn("Company Profile not found with ID: {}", id);
            return false;
        }
    }

    // ✅ Check by Email
    public boolean emailExists(String email) {
        logger.info("Checking if email exists: {}", email);
        return repository.existsByEmailAddress(email);
    }

    // ✅ Check by Company Name
    public boolean companyNameExists(String name) {
        logger.info("Checking if company name exists: {}", name);
        return repository.existsByCompanyName(name);
    }
}
