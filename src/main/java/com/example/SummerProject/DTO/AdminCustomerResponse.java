package com.example.SummerProject.DTO;

public class AdminCustomerResponse {

    private long id;

    private String customerName;

    private String emailId;

    private String address;

    public AdminCustomerResponse(long id, String customerName, String emailId, String address) {
        this.id = id;
        this.customerName = customerName;
        this.emailId = emailId;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return emailId;
    }

    public void setEmail(String email) {
        this.emailId = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

