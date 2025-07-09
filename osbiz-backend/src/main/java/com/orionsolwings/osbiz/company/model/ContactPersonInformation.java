package com.orionsolwings.osbiz.company.model;

public class ContactPersonInformation {

    private String name;
    private String contact;
    private String email;

    // Constructors
    public ContactPersonInformation() {
    }

    public ContactPersonInformation(String name, String contact, String email) {
        this.name = name;
        this.contact = contact;
        this.email = email;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
