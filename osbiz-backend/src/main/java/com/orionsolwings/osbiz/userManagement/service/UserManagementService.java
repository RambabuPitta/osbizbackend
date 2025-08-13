package com.orionsolwings.osbiz.userManagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orionsolwings.osbiz.common.enums.ApplicationModules;
import com.orionsolwings.osbiz.company.model.CompanyProfile;
import com.orionsolwings.osbiz.userManagement.model.LoginResponse;
import com.orionsolwings.osbiz.userManagement.model.PermissionFlags;
import com.orionsolwings.osbiz.userManagement.model.User;
import com.orionsolwings.osbiz.userManagement.repository.PermissionsRepository;
import com.orionsolwings.osbiz.userManagement.repository.UserManagementRepository;
import com.orionsolwings.osbiz.util.ApiResponses;
import com.orionsolwings.osbiz.util.JwtUtil;

@Service
public class UserManagementService {

	private static final Logger logger = LoggerFactory.getLogger(UserManagementService.class);

	private final UserManagementRepository userRepo;
	private final PermissionsRepository permissionRepo;
	private final ObjectMapper objectMapper;

	public UserManagementService(UserManagementRepository userRepo, PermissionsRepository permissionRepo,
			ObjectMapper objectMapper) {
		this.userRepo = userRepo;
		this.permissionRepo = permissionRepo;
		this.objectMapper = objectMapper;
	}

	// CREATE
	public User createUser(User user) {
		try {
			logger.info("Creating user: {}", objectMapper.writeValueAsString(user));
			return userRepo.save(user);
		} catch (Exception e) {
			logger.error("Failed to create user", e);
			throw new RuntimeException("User creation failed.");
		}
	}

	// READ - Get All Users
	public List<User> getAllUsers() {
		logger.info("Fetching all users");
		return userRepo.findAll();
	}

	// READ - Get User by ID
	public Optional<User> getUserById(String id) {
		logger.info("Fetching user by ID: {}", id);
		return userRepo.findById(id);
	}

	// UPDATE
	public User updateUser(String id, User updatedUser) {
		return userRepo.findById(id).map(existingUser -> {
			existingUser.setName(updatedUser.getName());
			existingUser.setEmail(updatedUser.getEmail());
			existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
			existingUser.setRole(updatedUser.getRole());
			existingUser.setPassword(updatedUser.getPassword());
			existingUser.setActive(updatedUser.isActive());
			logger.info("Updating user: {}", objectMapper.valueToTree(existingUser));
			return userRepo.save(existingUser);
		}).orElseThrow(() -> {
			logger.warn("User not found with ID: {}", id);
			return new RuntimeException("User not found.");
		});
	}

	// DELETE
	public void deleteUser(String id) {
		logger.info("Deleting user by ID: {}", id);
		if (userRepo.existsById(id)) {
			userRepo.deleteById(id);
			logger.info("User deleted successfully");
		} else {
			logger.warn("User not found to delete: {}", id);
			throw new RuntimeException("User not found to delete.");
		}
	}

	// CREATE PERMISSIONS
	public List<PermissionFlags> assignPermissions(List<PermissionFlags> permissions) {
		try {
			logger.info("Assigning permissions: {}", objectMapper.writeValueAsString(permissions));
			return permissionRepo.saveAll(permissions);
		} catch (Exception e) {
			logger.error("Failed to assign permissions", e);
			throw new RuntimeException("Permission assignment failed.");
		}
	}

	// READ ALL PERMISSIONS
	public List<PermissionFlags> getAllPermissions() {
		logger.info("Fetching all permissions");
		return permissionRepo.findAll();
	}

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public String createAdminUserFromCompanyProfile(CompanyProfile companyProfile) {
		logger.info("Starting admin user creation from company profile...");

		String email = companyProfile.getEmailAddress();
		String companyName = companyProfile.getCompanyName();

		User user = new User();
		user.setUserId(email);
		user.setName(companyName);
		user.setEmail(email);
		user.setPhoneNumber(companyProfile.getPhoneNumber());
		user.setPassword(passwordEncoder.encode(companyProfile.getPassword())); // Encrypt password
		user.setEmpId("ADMIN_" + System.currentTimeMillis());
		user.setRole("ADMIN");
		user.setActive(true);

		try {
			logger.debug("Saving admin user with email: {}", email);
			userRepo.save(user);
			logger.info("Admin user saved successfully: {}", email);
		} catch (Exception e) {
			logger.error("Error saving admin user: {}", email, e);
			return "Failed to save admin user.";
		}

		// Assign full permissions to all modules
		for (ApplicationModules module : ApplicationModules.values()) {
			try {
				
				List<String> cruds=new ArrayList<>();
				cruds.add("create");
				cruds.add("update");
				cruds.add("read");
				cruds.add("delete");
				PermissionFlags.PermissionId permissionId = new PermissionFlags.PermissionId();
				permissionId.setUserId(email);
				permissionId.setRole("ADMIN");
				permissionId.setModule(module.name());

				PermissionFlags permissions = new PermissionFlags();
				permissions.setId(permissionId);
				permissions.setPermissions(cruds);

				permissionRepo.save(permissions);
				logger.debug("Assigned permissions for module: {}", module.name());
			} catch (Exception e) {
				logger.error("Error assigning permission for module: {} to user: {}", module.name(), email, e);
			}
		}

		logger.info("Completed admin setup for email: {}", email);
		return "Admin user and permissions created from company profile.";
	}

//	public ApiResponses<List<PermissionFlags>> login(String userId, String rawPassword) {
//		logger.info("Attempting login for userId: {}", userId);
//
//		User user = userRepo.findByUserId(userId);
//
//		if (user == null) {
//			logger.warn("User not found: {}", userId);
//			return new ApiResponses<>("Invalid user ID or password", "FAILURE", null);
//		}
//
//		if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
//			logger.warn("Password mismatch for user: {}", userId);
//			return new ApiResponses<>("Invalid user ID or password", "FAILURE", null);
//		}
//
//		try {
//			List<PermissionFlags> permissions = permissionRepo.findByIdUserId(userId);
//			String jsonPermissions = objectMapper.writeValueAsString(permissions);
//			logger.info("User {} login successful. Permissions: {}", userId, jsonPermissions);
//			return new ApiResponses<>("Login successful", "SUCCESS", permissions);
//		} catch (Exception e) {
//			logger.error("Error retrieving permissions for user: {}", userId, e);
//			return new ApiResponses<>("Login successful but failed to fetch permissions", "PARTIAL_SUCCESS", null);
//		}
//	}

	
	public ApiResponses<LoginResponse> login(String userId, String rawPassword) {
	    logger.info("Attempting login for userId: {}", userId);

	    User user = userRepo.findByUserId(userId);

	    if (user == null) {
	        logger.warn("User not found: {}", userId);
	        return new ApiResponses<>("Invalid user ID or password", "FAILURE", null);
	    }

	    if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
	        logger.warn("Password mismatch for user: {}", userId);
	        return new ApiResponses<>("Invalid user ID or password", "FAILURE", null);
	    }

	    try {
	        List<PermissionFlags> permissions = permissionRepo.findByIdUserId(userId);
	        String token = JwtUtil.generateToken(user.getEmail());  // or userId, depending on your JWT subject design
	        
	        logger.info("the token is ---->>"+token);
	        
	        LoginResponse loginResponse = new LoginResponse(token, null);

	        logger.info("User {} login successful. Token generated.", userId);

	        return new ApiResponses<>("Login successful", "SUCCESS", loginResponse);
	    } catch (Exception e) {
	        logger.error("Error retrieving permissions for user: {}", userId, e);
	        return new ApiResponses<>("Login successful but failed to fetch permissions", "PARTIAL_SUCCESS", null);
	    }
	}

	
	public boolean checkIfEmailExists(String email) {
		return userRepo.existsByEmail(email);
	}

	public ApiResponses<String> setNewPassword(String email, String password) {
		User user = userRepo.findByUserId(email);

		if (user != null) {
			user.setPassword(passwordEncoder.encode(password)); 
			userRepo.save(user);
			return new ApiResponses<>("Password updated successfully", "success");
		} else {
			return new ApiResponses<>("Email not found", "failed");
		}
	}

	public User getUserByUsernameOrEmail(String identifier) {
	    User user = userRepo.findByUsername(identifier);
	    if (user == null) {
	        user = userRepo.findByEmail(identifier);
	    }
	    return user;
	}


}
