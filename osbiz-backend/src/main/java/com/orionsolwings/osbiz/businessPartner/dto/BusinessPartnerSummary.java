package com.orionsolwings.osbiz.businessPartner.dto;

public class BusinessPartnerSummary {

	private String id;
	private String businessCode;
	private String bpuid;
	private String businessPartner;

	private String entitytype;
	private String entityName;
	private String status;
	private String glAccount;
	private String billPolicy;
	private String emailid;
	private String phoneNumber;
	private String paymentType;

	// Getters and Setters

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getBpuid() {
		return bpuid;
	}

	public void setBpuid(String bpuid) {
		this.bpuid = bpuid;
	}

	public String getBusinessPartner() {
		return businessPartner;
	}

	public void setBusinessPartner(String businessPartner) {
		this.businessPartner = businessPartner;
	}

	public String getEntitytype() {
		return entitytype;
	}

	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGlAccount() {
		return glAccount;
	}

	public void setGlAccount(String glAccount) {
		this.glAccount = glAccount;
	}

	public String getBillPolicy() {
		return billPolicy;
	}

	public void setBillPolicy(String billPolicy) {
		this.billPolicy = billPolicy;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
