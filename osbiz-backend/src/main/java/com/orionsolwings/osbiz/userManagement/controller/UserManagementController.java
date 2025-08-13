package com.orionsolwings.osbiz.userManagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orionsolwings.osbiz.userManagement.dto.UserPermissionRequest;
import com.orionsolwings.osbiz.userManagement.model.LoginResponse;
import com.orionsolwings.osbiz.userManagement.model.PermissionFlags;
import com.orionsolwings.osbiz.userManagement.model.User;
import com.orionsolwings.osbiz.userManagement.service.UserManagementService;
import com.orionsolwings.osbiz.util.ApiResponses;
import com.orionsolwings.osbiz.util.EmailService;
import com.orionsolwings.osbiz.util.OtpService;

@RestController
@RequestMapping("/api/v1/usermanagement")
public class UserManagementController {

	private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

	private final UserManagementService userManagementService;
	private final ObjectMapper objectMapper;

	@Autowired
	OtpService otpService;

	@Autowired
	EmailService emailService;

	@Autowired
	UserManagementService userService;

	public UserManagementController(UserManagementService userManagementService, ObjectMapper objectMapper) {
		this.userManagementService = userManagementService;
		this.objectMapper = objectMapper;
	}

	@PostMapping("/user-with-permissions")
	public ResponseEntity<ApiResponses<Map<String, Object>>> createUserWithPermissions(
			@RequestBody UserPermissionRequest request) {
		try {
			logger.info("Creating user with permissions: {}", objectMapper.writeValueAsString(request));

			User createdUser = userManagementService.createUser(request.getUser());
			List<PermissionFlags> savedPermissions = userManagementService.assignPermissions(request.getPermissions());

			Map<String, Object> responseData = new HashMap<>();
			responseData.put("user", createdUser);
			responseData.put("permissions", savedPermissions);

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ApiResponses<>("User and permissions created successfully", "success", responseData));
		} catch (Exception e) {
			logger.error("Failed to create user with permissions", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponses<>("Failed to create user and permissions", "failure", "UMC_001", null));
		}
	}

	@GetMapping("/users")
	public ResponseEntity<ApiResponses<List<User>>> getAllUsers() {
		try {
			List<User> users = userManagementService.getAllUsers();
			return ResponseEntity.ok(new ApiResponses<>("Users fetched successfully", "success", users));
		} catch (Exception e) {
			logger.error("Failed to fetch users", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponses<>("Error fetching users", "failure", "UMC_002", null));
		}
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<ApiResponses<User>> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
		try {
			User user = userManagementService.updateUser(id, updatedUser);
			return ResponseEntity.ok(new ApiResponses<>("User updated successfully", "success", user));
		} catch (Exception e) {
			logger.error("Failed to update user", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponses<>("Error updating user", "failure", "UMC_003", null));
		}
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<ApiResponses<String>> deleteUser(@PathVariable String id) {
		try {
			userManagementService.deleteUser(id);
			return ResponseEntity.ok(new ApiResponses<>("User deleted successfully", "success"));
		} catch (Exception e) {
			logger.error("Failed to delete user", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponses<>("Error deleting user", "failure", "UMC_004", null));
		}
	}

	@GetMapping("/getPermissions")
	public ResponseEntity<ApiResponses<List<PermissionFlags>>> getAllPermissions() {
		try {
			List<PermissionFlags> permissions = userManagementService.getAllPermissions();
			return ResponseEntity.ok(new ApiResponses<>("Permissions fetched successfully", "success", permissions));
		} catch (Exception e) {
			logger.error("Failed to fetch permissions", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponses<>("Error fetching permissions", "failure", "UMC_005", null));
		}
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponses<LoginResponse>> login(@RequestBody User users) {
		String userId = users.getUserId();
		String password = users.getPassword();

		logger.info("Login request for userId: {}", userId);

		try {
			ApiResponses<LoginResponse> response = userManagementService.login(userId, password);
			logger.info("Login response: {}", objectMapper.writeValueAsString(response));
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("Login failed", e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ApiResponses<>("Invalid credentials", "failure", "UMC_006", null));
		}
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<ApiResponses<String>> forgotPassword(@RequestParam String email) {
		try {
			boolean emailExists = userService.checkIfEmailExists(email);
			if (!emailExists) {
				return ResponseEntity.badRequest()
						.body(new ApiResponses<>("Email not found", "failure", "UMC_007", null));
			}

			otpService.generateOtp(email);
			return ResponseEntity.ok(new ApiResponses<>("OTP sent to registered email", "success"));
		} catch (Exception e) {
			logger.error("Error in forgot password", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponses<>("Error sending OTP", "failure", "UMC_008", null));
		}
	}

	@PostMapping("/setNewPassword")
	public ResponseEntity<ApiResponses<String>> setNewPassword(@RequestParam String email,
			@RequestParam String password) {
		try {
			ApiResponses<String> response = userManagementService.setNewPassword(email, password);
			if ("success".equalsIgnoreCase(response.getStatus())) {
				return ResponseEntity.ok(response);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (Exception e) {
			logger.error("Error setting new password", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponses<>("Failed to set new password", "failure", "UMC_009", null));
		}
	}

	@GetMapping("/permissions")
	public ResponseEntity<ApiResponses<List<PermissionFlags>>> getUserPermissions() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			User loggedInUser = (User) authentication.getPrincipal();
			String email = loggedInUser.getEmail();

			logger.info("email => {}", email);

			List<PermissionFlags> permissions = userManagementService.getPermissionsByEmail(email);
			return ResponseEntity.ok(new ApiResponses<>("Permissions fetched successfully", "success", permissions));
		} catch (Exception e) {
			logger.error("Failed to fetch user permissions", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponses<>("Error fetching permissions", "failure", "UMC_010", null));
		}
	}

}

//@RestController
//@RequestMapping("/api/v1/usermanagement")
//public class UserManagementController {
//
//    private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);
//    private final UserManagementService userManagementService;
//    private final ObjectMapper objectMapper;
//    
//    @Autowired
//    OtpService otpService;
//
//    @Autowired
//    EmailService emailService;
//    
//    @Autowired
//    UserManagementService userService;
//
//    public UserManagementController(UserManagementService userManagementService, ObjectMapper objectMapper) {
//        this.userManagementService = userManagementService;
//        this.objectMapper = objectMapper;
//    }
//
//    /**
//     * POST: Create user with permissions
//     */
//    @PostMapping("/user-with-permissions")
//    public ResponseEntity<?> createUserWithPermissions(@RequestBody UserPermissionRequest request) {
//        try {
//            logger.info("Creating user with permissions: {}", objectMapper.writeValueAsString(request));
//
//            User createdUser = userManagementService.createUser(request.getUser());
//            List<PermissionFlags> savedPermissions = userManagementService.assignPermissions(request.getPermissions());
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("user", createdUser);
//            response.put("permissions", savedPermissions);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(response);
//        } catch (Exception e) {
//            logger.error("Failed to create user with permissions", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user and permissions.");
//        }
//    }
//
//    /**
//     * GET: All users
//     */
//    @GetMapping("/users")
//    public ResponseEntity<?> getAllUsers() {
//        try {
//            List<User> users = userManagementService.getAllUsers();
//            return ResponseEntity.ok(users);
//        } catch (Exception e) {
//            logger.error("Failed to fetch users", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching users.");
//        }
//    }
//
//    /**
//     * GET: Single user by ID
//     */
////    @GetMapping("/users/{id}")
////    public ResponseEntity<?> getUserById(@PathVariable String id) {
////        try {
////            return userManagementService.getUserById(id)
////                    .map(ResponseEntity::ok)
////                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found."));
////        } catch (Exception e) {
////            logger.error("Failed to fetch user by ID", e);
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user.");
////        }
////    }
//
//    /**
//     * PUT: Update user by ID
//     */
//    @PutMapping("/users/{id}")
//    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
//        try {
//            User user = userManagementService.updateUser(id, updatedUser);
//            return ResponseEntity.ok(user);
//        } catch (Exception e) {
//            logger.error("Failed to update user", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user.");
//        }
//    }
//
//    /**
//     * DELETE: Delete user by ID
//     */
//    @DeleteMapping("/users/{id}")
//    public ResponseEntity<?> deleteUser(@PathVariable String id) {
//        try {
//            userManagementService.deleteUser(id);
//            return ResponseEntity.ok("User deleted successfully.");
//        } catch (Exception e) {
//            logger.error("Failed to delete user", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user.");
//        }
//    }
//
//    /**
//     * GET: All permissions
//     */
//    @GetMapping("/getPermissions")
//    public ResponseEntity<?> getAllPermissions() {
//        try {
//            List<PermissionFlags> permissions = userManagementService.getAllPermissions();
//            return ResponseEntity.ok(permissions);
//        } catch (Exception e) {
//            logger.error("Failed to fetch permissions", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching permissions.");
//        }
//    }
//    
//    
////    @PostMapping("/login")
////    public ResponseEntity<ApiResponses<List<PermissionFlags>>> login(@RequestBody User users) {
////    	
////    	String userId=users.getUserId();
////    	String password=users.getPassword();
////    	
////
////        logger.info("Login request received for userId: {}", userId);
////
////        ApiResponses<List<PermissionFlags>> response = userManagementService.login(userId, password);
////
////        try {
////            String json = objectMapper.writeValueAsString(response);
////            logger.info("Login response: {}", json);
////        } catch (Exception e) {
////            logger.error("Failed to convert response to JSON for logging", e);
////        }
////
////        return ResponseEntity.ok(response);
////    }
//    
//    
//    @PostMapping("/login")
//    public ResponseEntity<ApiResponses<LoginResponse>> login(@RequestBody User users) {
//        String userId = users.getUserId();
//        String password = users.getPassword();
//
//        logger.info("Login request received for userId: {}", userId);
//
//        ApiResponses<LoginResponse> response = userManagementService.login(userId, password);
//
//        try {
//            String json = objectMapper.writeValueAsString(response);
//            logger.info("Login response: {}", json);
//        } catch (Exception e) {
//            logger.error("Failed to convert response to JSON for logging", e);
//        }
//
//        return ResponseEntity.ok(response);
//    }
//
//    
//    
//    @PostMapping("/forgot-password")
//	public ResponseEntity<ApiResponses<String>> forgotPassword(@RequestParam String email) {
//	    boolean emailExists = userService.checkIfEmailExists(email);
//	    if (!emailExists) {
//	        return ResponseEntity.badRequest().body(new ApiResponses<>("Email not found", "failure"));
//	    }
//
//	    String otp = otpService.generateOtp(email);
//
//	    return ResponseEntity.ok(new ApiResponses<>("OTP sent to registered email", "success"));
//	}
//    
//    @PostMapping("/setNewPassword")
//    public ResponseEntity<ApiResponses<String>> setNewPassword(@RequestParam String email, @RequestParam String password) {
//        ApiResponses<String> response = userManagementService.setNewPassword(email, password);
//        if ("success".equalsIgnoreCase(response.getStatus())) {
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//        }
//    }
//    
//  
//    @GetMapping("/permissions")
//    public ResponseEntity<List<PermissionFlags>> getUserPermissions() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();  // This is the email (username) set as principal in authentication
//
//        List<PermissionFlags> permissions = userManagementService.getPermissionsByEmail(email);
//
//        return ResponseEntity.ok(permissions);
//    }
//
//
//    
//    
//
//}
