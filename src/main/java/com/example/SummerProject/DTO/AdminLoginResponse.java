package com.example.SummerProject.DTO;

import java.util.List;

public class AdminLoginResponse {
    private List<AdminCustomerResponse> customers;
    private List<AdminShopResponse> shops;

    public AdminLoginResponse(List<AdminCustomerResponse> customers, List<AdminShopResponse> shops) {
        this.customers = customers;
        this.shops = shops;
    }

    public List<AdminCustomerResponse> getCustomers() {
        return customers;
    }

    public void setCustomers(List<AdminCustomerResponse> customers) {
        this.customers = customers;
    }

    public List<AdminShopResponse> getShops() {
        return shops;
    }

    public void setShops(List<AdminShopResponse> shops) {
        this.shops = shops;
    }
}
