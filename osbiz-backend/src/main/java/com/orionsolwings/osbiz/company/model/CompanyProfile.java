package com.orionsolwings.osbiz.company.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Document(collection = "company_profiles")
public class CompanyProfile {

    @Id
    private String id;

    private String companyName;
    private String emailAddress;
    private String phoneNumber;
    private String password;
    private String contactNumber;
    private String websiteURL;
    private String officeAddress;
    private String streetAddress;
    private String streetAddressLine2;
    private String city;
    private String state;
    private String postal;
    private String businessOrganizationType;

    private ContactPersonInformation contactPersonInformation;

    @Field("GLAccount")
    private String glAccount;

    @Field("Currency")
    private String currency;

    @Field("financialYear")
    private String fiscalYear;

    @Field("bankHolderName")
    private String bankHolderName;

    @Field("accountName")
    private String accountName;

    @Field("bankName")
    private String bankName;

    @Field("bankCode")
    private String bankCode;

    @Field("accountType")
    private String accountType;

    @Field("country")
    private String country;

    @Field("UPI")
    private String upi;

    private boolean signupAuth;
    private boolean verifiyCompanyProfile;
    private boolean adminAuth;
    private boolean checkboxConfirmation;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getWebsiteURL() {
		return websiteURL;
	}

	public void setWebsiteURL(String websiteURL) {
		this.websiteURL = websiteURL;
	}

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getStreetAddressLine2() {
		return streetAddressLine2;
	}

	public void setStreetAddressLine2(String streetAddressLine2) {
		this.streetAddressLine2 = streetAddressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getBusinessOrganizationType() {
		return businessOrganizationType;
	}

	public void setBusinessOrganizationType(String businessOrganizationType) {
		this.businessOrganizationType = businessOrganizationType;
	}

	public ContactPersonInformation getContactPersonInformation() {
		return contactPersonInformation;
	}

	public void setContactPersonInformation(ContactPersonInformation contactPersonInformation) {
		this.contactPersonInformation = contactPersonInformation;
	}

	public String getGlAccount() {
		return glAccount;
	}

	public void setGlAccount(String glAccount) {
		this.glAccount = glAccount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	public String getBankHolderName() {
		return bankHolderName;
	}

	public void setBankHolderName(String bankHolderName) {
		this.bankHolderName = bankHolderName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getUpi() {
		return upi;
	}

	public void setUpi(String upi) {
		this.upi = upi;
	}

	public boolean isSignupAuth() {
		return signupAuth;
	}

	public void setSignupAuth(boolean signupAuth) {
		this.signupAuth = signupAuth;
	}

	public boolean isVerifiyCompanyProfile() {
		return verifiyCompanyProfile;
	}

	public void setVerifiyCompanyProfile(boolean verifiyCompanyProfile) {
		this.verifiyCompanyProfile = verifiyCompanyProfile;
	}

	public boolean isAdminAuth() {
		return adminAuth;
	}

	public void setAdminAuth(boolean adminAuth) {
		this.adminAuth = adminAuth;
	}

	public boolean isCheckboxConfirmation() {
		return checkboxConfirmation;
	}

	public void setCheckboxConfirmation(boolean checkboxConfirmation) {
		this.checkboxConfirmation = checkboxConfirmation;
	}

	public LocalDate getCreatetedDate() {
		return createtedDate;
	}

	public void setCreatetedDate(LocalDate createtedDate) {
		this.createtedDate = createtedDate;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createtedDate;

    // Reference to RolePermission
    private String roleId;

    
}
