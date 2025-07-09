package com.orionsolwings.osbiz.userManagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orionsolwings.osbiz.userManagement.model.User;
import com.orionsolwings.osbiz.userManagement.model.PermissionFlags;
import com.orionsolwings.osbiz.userManagement.repository.UserManagementRepository;
import com.orionsolwings.osbiz.userManagement.repository.PermissionsRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserManagementService {

    private static final Logger logger = LoggerFactory.getLogger(UserManagementService.class);

    private final UserManagementRepository userRepo;
    private final PermissionsRepository permissionRepo;
    private final ObjectMapper objectMapper;

    public UserManagementService(UserManagementRepository userRepo, PermissionsRepository permissionRepo, ObjectMapper objectMapper) {
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
}
