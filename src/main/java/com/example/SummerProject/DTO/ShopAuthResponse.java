package com.example.SummerProject.DTO;

public class ShopAuthResponse {
    private String token;
    private String name;
    private String shopName;
    private String emailId;
    private Long shopId;

    public ShopAuthResponse(String token, String name, String shopName, String emailId, Long shopId) {
        this.token = token;
        this.name = name;
        this.shopName = shopName;
        this.emailId = emailId;
        this.shopId = shopId;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getShopName() {
        return shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    public Long getShopId() {
        return shopId;
    }
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
