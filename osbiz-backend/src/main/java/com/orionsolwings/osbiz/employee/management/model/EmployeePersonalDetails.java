package com.orionsolwings.osbiz.employee.management.model;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.index.Indexed;

public class EmployeePersonalDetails {

    private String employeeName;
    private String gender;
    private LocalDate dateOfBirth;
    private LocalDate dateOfJoining;
    private String department;
    private String designation;

    @Indexed(unique = true)
    private String panNumber;

    @Indexed(unique = true)
    private String aadharNumber;

    @Indexed(unique = true)
    private String passportNumber;

    @Indexed(unique = true)
    private String drivingLicenseNumber;

    private boolean enablePortal;

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDate getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(LocalDate dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getDrivingLicenseNumber() {
		return drivingLicenseNumber;
	}

	public void setDrivingLicenseNumber(String drivingLicenseNumber) {
		this.drivingLicenseNumber = drivingLicenseNumber;
	}

	public boolean isEnablePortal() {
		return enablePortal;
	}

	public void setEnablePortal(boolean enablePortal) {
		this.enablePortal = enablePortal;
	}

    // Getters and Setters...
}
