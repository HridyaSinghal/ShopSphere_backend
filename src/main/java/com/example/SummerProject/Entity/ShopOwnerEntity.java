package com.example.SummerProject.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ShopOwnerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String shopName;

    @NotBlank
    private String phoneNo;

    @Email
    @NotBlank
    private String emailId;

    @NotBlank
    private String password;

    private String address;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>(); // ✅ fixed: mutable list

    private String storeLink;

    private String retailerEmail;

    @OneToOne(mappedBy = "shopOwner", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonBackReference
    private ShopEntity shop;

    // ✅ Default constructor with default role assignment
    public ShopOwnerEntity() {
        this.roles.add("ROLE_SHOPOWNER");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoreLink() {
        return storeLink;
    }

    public void setStoreLink(String storeLink) {
        this.storeLink = storeLink;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = new ArrayList<>(roles); // ✅ ensures it's a mutable list
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public ShopEntity getShop() {
        return shop;
    }

    public void setShop(ShopEntity shop){
    this.shop = shop;
    }

    public String getRetailerEmail() {
        return retailerEmail;
    }
    public void setRetailerEmail(String retailerEmail) {
        this.retailerEmail = retailerEmail;
    }
}