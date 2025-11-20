package com.example.SummerProject.DTO;

import java.util.Set;

public class UserAuthResponse {
    private String jwt;
    private String userName;
    private String email;
    private String userRole;
    private String address;

    public UserAuthResponse(String jwt, String userName, String email, String userRole, String address) {
        this.jwt = jwt;
        this.userName = userName;
        this.email = email;
        this.userRole = userRole;
        this.address = address;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
