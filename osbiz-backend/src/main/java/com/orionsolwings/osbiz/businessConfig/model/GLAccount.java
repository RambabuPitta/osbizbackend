package com.orionsolwings.osbiz.businessConfig.model;

import java.time.ZonedDateTime;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "gl_accounts")
public class GLAccount {

	@Id
	private String id;
	private String businessCode;
	private String glAccount;
	private String entityType;
	private String billPolicy;
	private String accountType;
	private String status;
	private ZonedDateTime createdDate;
	private ZonedDateTime updatedDate;

	@Override
	public int hashCode() {
		return Objects.hash(accountType, billPolicy, businessCode, createdDate, entityType, glAccount, id, status,
				updatedDate);
	}

	@Override

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GLAccount other = (GLAccount) obj;
		return Objects.equals(accountType, other.accountType) && Objects.equals(billPolicy, other.billPolicy)
				&& Objects.equals(businessCode, other.businessCode) && Objects.equals(createdDate, other.createdDate)
				&& Objects.equals(entityType, other.entityType) && Objects.equals(glAccount, other.glAccount)
				&& Objects.equals(id, other.id) && Objects.equals(status, other.status)
				&& Objects.equals(updatedDate, other.updatedDate);
	}

	@Override
	public String toString() {
		return "GLAccount [id=" + id + ", businessCode=" + businessCode + ", glAccount=" + glAccount + ", entityType="
				+ entityType + ", billPolicy=" + billPolicy + ", accountType=" + accountType + ", status=" + status
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getGlAccount() {
		return glAccount;
	}

	public void setGlAccount(String glAccount) {
		this.glAccount = glAccount;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getBillPolicy() {
		return billPolicy;
	}

	public void setBillPolicy(String billPolicy) {
		this.billPolicy = billPolicy;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ZonedDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(ZonedDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public ZonedDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(ZonedDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	// Getters and Setters
	// (Omitted here for brevity - generate via IDE or Lombok if preferred)
}