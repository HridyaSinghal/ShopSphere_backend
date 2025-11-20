package com.example.SummerProject.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class ShopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long shop_id;

    private String shopOwnerName;

    private String shopName;


    private long totalEarning;

    private String link;

    private String retailerEmail; // Email to notify for low stock

    @OneToOne
    @JoinColumn(name = "shop_owner_id")
    private ShopOwnerEntity shopOwner;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonManagedReference
    private List<ProductEntity> products;


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getShop_id() {
        return shop_id;
    }

    public void setShop_id(long shop_id) {
        this.shop_id = shop_id;
    }





    public ShopOwnerEntity getShopOwner() {
        return shopOwner;
    }

    public void setShopOwner(ShopOwnerEntity shopOwner) {
        this.shopOwner = shopOwner;
    }

    public long getId() {
        return shop_id;
    }

    public void setId(long id) {
        this.shop_id = id;
    }

    public String getShopOwnerName() {
        return shopOwnerName;
    }

    public void setShopOwnerName(String shopOwnerName) {
        this.shopOwnerName = shopOwnerName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public long getTotalEarning() {
        return totalEarning;
    }

    public void setTotalEarning(long totalEarning) {
        this.totalEarning = totalEarning;
    }

    public String getRetailerEmail() {
        return retailerEmail;
    }
    public void setRetailerEmail(String retailerEmail) {
        this.retailerEmail = retailerEmail;
    }

}
