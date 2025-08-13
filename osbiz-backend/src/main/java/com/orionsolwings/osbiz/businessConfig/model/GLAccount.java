package com.orionsolwings.osbiz.businessConfig.model;

import java.util.Date;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;

@Document(collection = "gl_accounts")

@CompoundIndexes({
    @CompoundIndex(name = "unique_businessCode_accountType", 
                   def = "{'businessCode': 1, 'accountType': 1}", 
                   unique = true)
})
public class GLAccount {

	@Id
	private String id;
	
	private String businessCode;
	
	@NotBlank(message = "glAccount is required")
	@Indexed(unique = true)
	private String glAccount;

    @NotBlank(message = "entityType is required")
    private String entityType;

    @NotBlank(message = "billPolicy is required")
    private String billPolicy;

    @NotBlank(message = "accountType is required")
    private String accountType;
    
    
	private Date createdDate;

	@Override
	public int hashCode() {
		return Objects.hash(accountType, billPolicy, businessCode, createdDate, entityType, glAccount, id);
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
				&& Objects.equals(id, other.id) ;
	}

	@Override
	public String toString() {
		return "GLAccount [id=" + id + ", businessCode=" + businessCode + ", glAccount=" + glAccount + ", entityType="
				+ entityType + ", billPolicy=" + billPolicy + ", accountType=" + accountType + ", status=" + 
				", createdDate=" + createdDate + ", updatedDate="  + "]";
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



	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
//
//	public Date getUpdatedDate() {
//		return updatedDate;
//	}
//
//	public void setUpdatedDate(Date updatedDate) {
//		this.updatedDate = updatedDate;
//	}

	
	// Getters and Setters
	// (Omitted here for brevity - generate via IDE or Lombok if preferred)
}