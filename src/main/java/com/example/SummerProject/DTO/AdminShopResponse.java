package com.example.SummerProject.DTO;

public class AdminShopResponse {

    private Long id;

    private String ShopOwnerName;

    private String shopName;

    private String shopAddress;

    public AdminShopResponse(long id, String shopOwnerName, String shopName, String shopAddress) {
        this.id = id;
        this.ShopOwnerName = shopOwnerName;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShopOwnerName() {
        return ShopOwnerName;
    }

    public void setShopOwnerName(String shopOwnerName) {
        ShopOwnerName = shopOwnerName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return shopAddress;
    }

    public void setAddress(String address) {
        this.shopAddress = address;
    }
}
