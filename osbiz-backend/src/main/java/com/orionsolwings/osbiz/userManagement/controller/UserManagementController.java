package com.orionsolwings.osbiz.userManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orionsolwings.osbiz.userManagement.dto.UserPermissionRequest;
import com.orionsolwings.osbiz.userManagement.model.PermissionFlags;
import com.orionsolwings.osbiz.userManagement.model.User;
import com.orionsolwings.osbiz.userManagement.service.UserManagementService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/usermanagement")
public class UserManagementController {

    private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);
    private final UserManagementService userManagementService;
    private final ObjectMapper objectMapper;

    public UserManagementController(UserManagementService userManagementService, ObjectMapper objectMapper) {
        this.userManagementService = userManagementService;
        this.objectMapper = objectMapper;
    }

    /**
     * POST: Create user with permissions
     */
    @PostMapping("/user-with-permissions")
    public ResponseEntity<?> createUserWithPermissions(@RequestBody UserPermissionRequest request) {
        try {
            logger.info("Creating user with permissions: {}", objectMapper.writeValueAsString(request));

            User createdUser = userManagementService.createUser(request.getUser());
            List<PermissionFlags> savedPermissions = userManagementService.assignPermissions(request.getPermissions());

            Map<String, Object> response = new HashMap<>();
            response.put("user", createdUser);
            response.put("permissions", savedPermissions);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.error("Failed to create user with permissions", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user and permissions.");
        }
    }

    /**
     * GET: All users
     */
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userManagementService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Failed to fetch users", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching users.");
        }
    }

    /**
     * GET: Single user by ID
     */
//    @GetMapping("/users/{id}")
//    public ResponseEntity<?> getUserById(@PathVariable String id) {
//        try {
//            return userManagementService.getUserById(id)
//                    .map(ResponseEntity::ok)
//                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found."));
//        } catch (Exception e) {
//            logger.error("Failed to fetch user by ID", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user.");
//        }
//    }

    /**
     * PUT: Update user by ID
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        try {
            User user = userManagementService.updateUser(id, updatedUser);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("Failed to update user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user.");
        }
    }

    /**
     * DELETE: Delete user by ID
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        try {
            userManagementService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully.");
        } catch (Exception e) {
            logger.error("Failed to delete user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user.");
        }
    }

    /**
     * GET: All permissions
     */
    @GetMapping("/permissions")
    public ResponseEntity<?> getAllPermissions() {
        try {
            List<PermissionFlags> permissions = userManagementService.getAllPermissions();
            return ResponseEntity.ok(permissions);
        } catch (Exception e) {
            logger.error("Failed to fetch permissions", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching permissions.");
        }
    }
}
