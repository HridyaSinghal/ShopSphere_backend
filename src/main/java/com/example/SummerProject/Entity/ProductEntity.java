    package com.example.SummerProject.Entity;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import jakarta.persistence.*;

    import java.util.List;

    @Entity
    public class ProductEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long pro_id;

            private String productName;

            private String price;

            private int quantity;

            private String productImg;

            private String productDetails;

        private String category;

        @ManyToOne
        @JoinColumn(name = "shop_id")
        @JsonBackReference
        private ShopEntity shop;








        public long getPro_id() {
            return pro_id;
        }

        public void setPro_id(long pro_id) {
            this.pro_id = pro_id;
        }

        public ShopEntity getShop() {
            return shop;
        }

        public void setShop(ShopEntity shop) {
            this.shop = shop;
        }



        public long getId() {
            return pro_id;
        }

        public void setId(long id) {
            this.pro_id = id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getProductImg() {
            return productImg;
        }

        public void setProductImg(String productImg) {
            this.productImg = productImg;
        }

        public String getProductDetails() {
            return productDetails;
        }

        public void setProductDetails(String productDetails) {
            this.productDetails = productDetails;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }


    }
