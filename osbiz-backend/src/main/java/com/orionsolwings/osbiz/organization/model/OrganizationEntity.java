package com.orionsolwings.osbiz.organization.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Objects;

@Document(collection = "organization")
public class OrganizationEntity {

    @Id
    private String id;

    private String companyName;
    private String contactNumber;
    private String companyEmail;
    private String websiteUrl;

    private String streetAddress;
    private String streetAddressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String businessType;

    private boolean companyProfile;
    private boolean confirmation;

    private String contactPersonName;
    private String contactPersonEmail;
    private String contactPersonNumber;

    private LocalDate date;

    // Getters and Setters

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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public boolean isCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(boolean companyProfile) {
        this.companyProfile = companyProfile;
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

    public String getContactPersonNumber() {
        return contactPersonNumber;
    }

    public void setContactPersonNumber(String contactPersonNumber) {
        this.contactPersonNumber = contactPersonNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // toString

    @Override
    public String toString() {
        return "OrganizationEntity{" +
                "id='" + id + '\'' +
                ", companyName='" + companyName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", companyEmail='" + companyEmail + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", streetAddressLine2='" + streetAddressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", businessType='" + businessType + '\'' +
                ", companyProfile=" + companyProfile +
                ", confirmation=" + confirmation +
                ", contactPersonName='" + contactPersonName + '\'' +
                ", contactPersonEmail='" + contactPersonEmail + '\'' +
                ", contactPersonNumber='" + contactPersonNumber + '\'' +
                ", date=" + date +
                '}';
    }

    // equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationEntity)) return false;
        OrganizationEntity that = (OrganizationEntity) o;
        return companyProfile == that.companyProfile &&
                confirmation == that.confirmation &&
                Objects.equals(id, that.id) &&
                Objects.equals(companyName, that.companyName) &&
                Objects.equals(contactNumber, that.contactNumber) &&
                Objects.equals(companyEmail, that.companyEmail) &&
                Objects.equals(websiteUrl, that.websiteUrl) &&
                Objects.equals(streetAddress, that.streetAddress) &&
                Objects.equals(streetAddressLine2, that.streetAddressLine2) &&
                Objects.equals(city, that.city) &&
                Objects.equals(state, that.state) &&
                Objects.equals(postalCode, that.postalCode) &&
                Objects.equals(businessType, that.businessType) &&
                Objects.equals(contactPersonName, that.contactPersonName) &&
                Objects.equals(contactPersonEmail, that.contactPersonEmail) &&
                Objects.equals(contactPersonNumber, that.contactPersonNumber) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyName, contactNumber, companyEmail, websiteUrl,
                streetAddress, streetAddressLine2, city, state, postalCode, businessType,
                companyProfile, confirmation, contactPersonName, contactPersonEmail, contactPersonNumber, date);
    }
}
