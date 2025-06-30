package com.orionsolwings.osbiz.client.model;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Document(collection = "client_accounts")
public class ClientModel {

	@Id
	@NotBlank(message = "Email address is required")
	@Email(message = "Invalid email format")
	private String emailAddress;

	@NotBlank(message = "Company name is required")
	private String companyName;

	@NotBlank(message = "Phone number is required")
	@Indexed(unique = true)
	private String phoneNumber;

	@NotBlank(message = "Password is required")
	@Size(min = 6, message = "Password must be at least 6 characters long")
	private String password;

	// Getters and Setters

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	// equals(), hashCode(), toString()

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		ClientModel other = (ClientModel) obj;
		return Objects.equals(companyName, other.companyName) && Objects.equals(phoneNumber, other.phoneNumber)
				&& Objects.equals(emailAddress, other.emailAddress) && Objects.equals(password, other.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(companyName, phoneNumber, emailAddress, password);
	}

	@Override
	public String toString() {
		return "ClientModel{" + "emailAddress='" + emailAddress + '\'' + ", companyName='" + companyName + '\''
				+ ", phoneNumber='" + phoneNumber + '\'' + ", password='[PROTECTED]'" + '}';
	}
}
