package com.orionsolwings.osbiz.userManagement.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
//import javax.validation.constraints.NotBlank;

@Document(collection = "users")
public class User {

	@Id
	private String id;

	@NotBlank(message = "User ID is required")
	@Indexed(unique = true)
	private String userId;

	private String name;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private String role; // e.g., "ADMIN", "USER", etc.
	private String password;
	@NotBlank(message = "Email is required")
	@Indexed(unique = true)
	private String email;

	@NotBlank(message = "Phone number is required")
	@Indexed(unique = true)
	private String phoneNumber;

	@NotBlank(message = "Employee ID is required")
	@Indexed(unique = true)
	private String empId;

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	private boolean active;

	// Constructors
	public User() {
	}

	public User(String name, String email, String phoneNumber, String password, String role, boolean active) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.role = role;
		this.active = active;
	}

	// Getters and Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
