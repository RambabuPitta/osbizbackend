package com.orionsolwings.osbiz.employee.management.model;

public class Employee {
	private String employeeId;
	private String emailId;
	private String phoneNumber;
	private EmployeePersonalDetails personalDetails;
	private AddressDetails addressDetails;
	private BankDetails bankDetails;
	private EmergencyDetails emergencyDetails;
	private WorkExperience workExperience;
	private SalaryDetails salaryDetails;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public EmployeePersonalDetails getPersonalDetails() {
		return personalDetails;
	}

	public void setPersonalDetails(EmployeePersonalDetails personalDetails) {
		this.personalDetails = personalDetails;
	}

	public AddressDetails getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(AddressDetails addressDetails) {
		this.addressDetails = addressDetails;
	}

	public BankDetails getBankDetails() {
		return bankDetails;
	}

	public void setBankDetails(BankDetails bankDetails) {
		this.bankDetails = bankDetails;
	}

	public EmergencyDetails getEmergencyDetails() {
		return emergencyDetails;
	}

	public void setEmergencyDetails(EmergencyDetails emergencyDetails) {
		this.emergencyDetails = emergencyDetails;
	}

	public WorkExperience getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(WorkExperience workExperience) {
		this.workExperience = workExperience;
	}

	public SalaryDetails getSalaryDetails() {
		return salaryDetails;
	}

	public void setSalaryDetails(SalaryDetails salaryDetails) {
		this.salaryDetails = salaryDetails;
	}

}
