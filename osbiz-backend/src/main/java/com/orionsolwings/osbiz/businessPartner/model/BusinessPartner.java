package com.orionsolwings.osbiz.businessPartner.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotBlank;

@Document(collection = "business_partner")
public class BusinessPartner {

//	@Id
//	private String id; // This maps to MongoDB's "_id"

	@NotBlank(message = "bpuid cannot be null or blank")
	@Indexed(unique = true)
	@Field("bpuid")
	private String bpuid;

	@Field("entity_type")
	private String entityType;

	@Field("business_code")
	private String businessCode;
	
	private String status; 
	
	public String getGlAccount() {
		return glAccount;
	}

	public void setGlAccount(String glAccount) {
		this.glAccount = glAccount;
	}

	private String glAccount;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	@Field("entity_name")
	private String entityName;

	@Field("business_partner_name")
	private String businessPartnerName;

	@Field("bill_policy")
	private String billPolicy;

	@Field("tax_vat")
	private String taxVat;

	@Field("email_address")
	private String emailAddress;

	@Field("phone_number")
	private String phoneNumber;

	@Field("payment_type")
	private String paymentType;

	@Field("shipping_address")
	private Address shippingAddress;

	@Field("billing_address")
	private Address billingAddress;

	@Field("bank_holder_name")
	private String bankHolderName;

	@Field("account_number")
	private String accountNumber;

	@Field("re_account_number")
	private String reAccountNumber;

	@Field("bank_ifsc")
	private String bankIFSC;

	@Field("code_name")
	private String bankName;

	@Field("currency")
	private String currency;

	@Field("account_type")
	private String accountType;

	@Field("country")
	private String country;

	@Field("primary_responder")
	private Responder primaryResponder;

	@Field("secondary_responder")
	private Responder secondaryResponder;

	@Field("invoice_send_email")
	private boolean invoiceSendEmail;

	@Field("invoice_send_whatsapp")
	private boolean invoiceSendWhatsapp;

	@Field("third_party_payment")
	private boolean thirdPartyPayment;

	@Field("tax_applicable")
	private boolean taxApplicable;

	@Field("branding_promotions")
	private boolean brandingPromotions;

	public static class Address {
		private String street;
		private String country;
		private String city;
		private String zip;
		private String state;
		private String emailAddress;
		private String phoneNumber;

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getZip() {
			return zip;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
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
	}

	public String getBpuid() {
		return bpuid;
	}

	public void setBpuid(String bpuid) {
		this.bpuid = bpuid;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getBusinessPartnerName() {
		return businessPartnerName;
	}

	public void setBusinessPartnerName(String businessPartnerName) {
		this.businessPartnerName = businessPartnerName;
	}

	public String getBillPolicy() {
		return billPolicy;
	}

	public void setBillPolicy(String billPolicy) {
		this.billPolicy = billPolicy;
	}

	public String getTaxVat() {
		return taxVat;
	}

	public void setTaxVat(String taxVat) {
		this.taxVat = taxVat;
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

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getBankHolderName() {
		return bankHolderName;
	}

	public void setBankHolderName(String bankHolderName) {
		this.bankHolderName = bankHolderName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getReAccountNumber() {
		return reAccountNumber;
	}

	public void setReAccountNumber(String reAccountNumber) {
		this.reAccountNumber = reAccountNumber;
	}

	public String getBankIFSC() {
		return bankIFSC;
	}

	public void setBankIFSC(String bankIFSC) {
		this.bankIFSC = bankIFSC;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public Responder getPrimaryResponder() {
		return primaryResponder;
	}

	public void setPrimaryResponder(Responder primaryResponder) {
		this.primaryResponder = primaryResponder;
	}

	public Responder getSecondaryResponder() {
		return secondaryResponder;
	}

	public void setSecondaryResponder(Responder secondaryResponder) {
		this.secondaryResponder = secondaryResponder;
	}

	public boolean isInvoiceSendEmail() {
		return invoiceSendEmail;
	}

	public void setInvoiceSendEmail(boolean invoiceSendEmail) {
		this.invoiceSendEmail = invoiceSendEmail;
	}

	public boolean isInvoiceSendWhatsapp() {
		return invoiceSendWhatsapp;
	}

	public void setInvoiceSendWhatsapp(boolean invoiceSendWhatsapp) {
		this.invoiceSendWhatsapp = invoiceSendWhatsapp;
	}

	public boolean isThirdPartyPayment() {
		return thirdPartyPayment;
	}

	public void setThirdPartyPayment(boolean thirdPartyPayment) {
		this.thirdPartyPayment = thirdPartyPayment;
	}

	public boolean isTaxApplicable() {
		return taxApplicable;
	}

	public void setTaxApplicable(boolean taxApplicable) {
		this.taxApplicable = taxApplicable;
	}

	public boolean isBrandingPromotions() {
		return brandingPromotions;
	}

	public void setBrandingPromotions(boolean brandingPromotions) {
		this.brandingPromotions = brandingPromotions;
	}

	public static class Responder {
		private String name;
		private String contactNumber;
		private String emailAddress;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getContactNumber() {
			return contactNumber;
		}

		public void setContactNumber(String contactNumber) {
			this.contactNumber = contactNumber;
		}

		public String getEmailAddress() {
			return emailAddress;
		}

		public void setEmailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
		}
	}

}
