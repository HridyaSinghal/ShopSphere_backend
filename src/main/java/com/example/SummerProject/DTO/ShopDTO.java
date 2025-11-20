    package com.example.SummerProject.DTO;

    public class ShopDTO {
        private Long shopId;
        private String shopName;
        private String shopOwnerName;
        private Long totalEarning;
        private String link;
        private String shopOwnerAddress;


        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public Long getShopId() {
            return shopId;
        }

        public void setShopId(Long shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopOwnerName() {
            return shopOwnerName;
        }

        public void setShopOwnerName(String shopOwnerName) {
            this.shopOwnerName = shopOwnerName;
        }

        public Long getTotalEarning() {
            return totalEarning;
        }

        public void setTotalEarning(Long totalEarning) {
            this.totalEarning = totalEarning;
        }

        public String getShopOwnerAddress() {
            return shopOwnerAddress;
        }

        public void setShopOwnerAddress(String shopOwnerAddress) {
            this.shopOwnerAddress = shopOwnerAddress;
        }
    }

