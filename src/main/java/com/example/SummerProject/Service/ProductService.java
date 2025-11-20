//package com.example.SummerProject.Service;
//
//import com.example.SummerProject.Entity.ProductEntity;
//import com.example.SummerProject.Entity.ShopEntity;
//import com.example.SummerProject.Repository.ProductRepository;
//import com.example.SummerProject.Repository.ShopsRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ProductService {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private ShopsRepository shopsRepository;
////create new product for shop
//    public String createProduct(ProductEntity productEntity) {
//        productRepository.save(productEntity);
//        return "product created";
//    }
//    // search specific product from the shop
//    public List<ProductEntity> getSpecificProduct(Long shopId, String item) {
//        return productRepository.findByShopIdAndProductNameContainingIgnoreCase(shopId, item);
//    }
//
//
//    public String deleteProductFromShop(Long shopId, Long proId) {
//        ShopEntity shop = shopsRepository.findById(shopId).orElse(null);
//        if (shop == null) return "Shop not found";
//
//        ProductEntity toRemove = null;
//        for (ProductEntity product : shop.getProducts()) {
//            if (product.getPro_id() == proId) {
//                toRemove = product;
//                break;
//            }
//        }
//
//        if (toRemove != null) {
//            shop.getProducts().remove(toRemove);
//            shopsRepository.save(shop); // orphanRemoval will delete the product
//            return "Product deleted";
//        }
//
//        return "Product not found in shop";
//
//    }
//
//
//    public String updateProduct(ProductEntity productEntity) {
//
//        productRepository.save(productEntity);
//
//        return "product updated successfully";
//    }
//
//
//    public List<ProductEntity> getAllProductsByShopId(Long shopId) {
//        return productRepository.findByShopId(shopId);
//    }
//    // Get all products of a specific shop by shop name and shop id
//    public List<ProductEntity> getAllProductsByShopNameAndId(String shopName, Long shopId) {
//        // Optionally, you can validate shopName here if needed
//        return productRepository.findByShopId(shopId);
//    }
//
//
//
//
//
//
//
//
//}


package com.example.SummerProject.Service;

import com.example.SummerProject.Entity.ProductEntity;
import com.example.SummerProject.Entity.ShopEntity;
import com.example.SummerProject.Repository.ProductRepository;
import com.example.SummerProject.Repository.ShopsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.HashSet;
import java.util.Set;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.annotation.PostConstruct;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShopsRepository shopsRepository;

    @Autowired
    private JavaMailSender mailSender;

    // Track which products have already triggered a low stock email to avoid spamming
    private Set<Long> lowStockNotified = new HashSet<>();

    // Removed @PostConstruct runLowStockCheckOnStartup method

    // Scheduled task: check every 1 minute for low stock products
    @Scheduled(fixedRate = 30000) // every 60,000 ms = 1 min
    public void checkLowStockAndNotify() {
        logger.info("[Scheduler] Checking for low stock products...");
        List<ProductEntity> allProducts = productRepository.findAll();
        for (ProductEntity product : allProducts) {
        try {
            int qty = product.getQuantity();
            String retailerEmail = (product.getShop() != null) ? product.getShop().getRetailerEmail() : null;
            logger.info("[Scheduler] Product '{}' (ID: {}), Qty: {}, RetailerEmail: {}", product.getProductName(), product.getPro_id(), qty, retailerEmail);
            if (qty < 10 && product.getShop() != null) {
                ShopEntity shop = product.getShop();
                if (shop.getRetailerEmail() != null && !shop.getRetailerEmail().isEmpty()) {
                    // Only send if not already notified for this product
                    if (!lowStockNotified.contains(product.getPro_id())) {
                        SimpleMailMessage message = new SimpleMailMessage();
                        message.setTo(shop.getRetailerEmail());
                        message.setSubject("Low Stock Alert: " + product.getProductName());
                        message.setText("The quantity for product '" + product.getProductName() + "' is now " + qty + ". Please restock soon.");
                        try {
                            mailSender.send(message);
                            lowStockNotified.add(product.getPro_id());
                            logger.info("[Scheduler] Low stock email sent for product '{}' (ID: {}) to {}. Qty: {}", product.getProductName(), product.getPro_id(), shop.getRetailerEmail(), qty);
                        } catch (Exception mailEx) {
                            logger.error("[Scheduler] Failed to send email for product '{}' (ID: {}): {}", product.getProductName(), product.getPro_id(), mailEx.getMessage());
                        }
                    } else {
                        logger.info("[Scheduler] Email already sent for product '{}' (ID: {}). Skipping.", product.getProductName(), product.getPro_id());
                    }
                } else {
                    logger.warn("[Scheduler] Retailer email not set for shop of product '{}' (ID: {}).", product.getProductName(), product.getPro_id());
                }
            } else {
                // If quantity is back to 10 or more, allow future notifications
                if (lowStockNotified.remove(product.getPro_id())) {
                    logger.info("[Scheduler] Product '{}' (ID: {}) restocked to {}. Notification reset.", product.getProductName(), product.getPro_id(), qty);
                } else {
                    logger.info("[Scheduler] Product '{}' (ID: {}) has sufficient stock ({}). No email sent.", product.getProductName(), product.getPro_id(), qty);
                }
            }
        } catch (Exception e) {
            logger.error("[Scheduler] Error checking product ID {}: {}", product.getPro_id(), e.getMessage());
        }
        }
    }

    // ✅ Create new product for a shop using shopId
    public String createProduct(Long shopId, ProductEntity productEntity) {
        ShopEntity shop = shopsRepository.findById(shopId).orElse(null);
        if (shop == null) return "Shop not found";

        productEntity.setShop(shop); // set the relationship
        productRepository.save(productEntity);

        return "Product created successfully";
    }

    // ✅ Search specific product from the shop
    public List<ProductEntity> getSpecificProduct(Long shopId, String item) {
        return productRepository.findByShopIdAndProductNameContainingIgnoreCase(shopId, item);
    }

    // ✅ Delete product from shop
    public String deleteProductFromShop(Long shopId, Long proId) {
        ShopEntity shop = shopsRepository.findById(shopId).orElse(null);
        if (shop == null) return "Shop not found";

        ProductEntity toRemove = null;
        for (ProductEntity product : shop.getProducts()) {
            if (proId != null && proId.equals(product.getPro_id())){
                toRemove = product;
                break;
            }
        }

        if (toRemove != null) {
            shop.getProducts().remove(toRemove);
            shopsRepository.save(shop); // orphanRemoval handles deletion
            return "Product deleted";
        }

        return "Product not found in shop";
    }

    // ✅ Update product with shopId (ensure product belongs to shop)
    public String updateProduct(Long shopId, ProductEntity productEntity) {
        ShopEntity shop = shopsRepository.findById(shopId).orElse(null);
        if (shop == null) return "Shop not found";

        productEntity.setShop(shop); // set shop again if needed
        productRepository.save(productEntity);

        // Send email if quantity < 10
        try {
            int qty = productEntity.getQuantity();
            if (qty < 10 && shop.getRetailerEmail() != null && !shop.getRetailerEmail().isEmpty()) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(shop.getRetailerEmail());
                message.setSubject("Low Stock Alert: " + productEntity.getProductName());
                message.setText("The quantity for product '" + productEntity.getProductName() + "' is now " + qty + ". Please restock soon.");
                try {
                    mailSender.send(message);
                } catch (Exception mailEx) {
                    logger.error("[UpdateProduct] Failed to send email for product '{}' (ID: {}): {}", productEntity.getProductName(), productEntity.getPro_id(), mailEx.getMessage());
                }
            }
        } catch (Exception e) {
            logger.error("[UpdateProduct] Error parsing quantity for product '{}' (ID: {}): {}", productEntity.getProductName(), productEntity.getPro_id(), e.getMessage());
        }

        return "Product updated successfully";
    }

    // ✅ Get all products of a shop by shopId
    public List<ProductEntity> getAllProductsByShopId(Long shopId) {
        return productRepository.findByShopId(shopId);
    }

    // ✅ Optional: Get products by shop name and ID (used previously)
    public List<ProductEntity> getAllProductsByShopNameAndId(String shopName, Long shopId) {
        return productRepository.findByShopId(shopId);
    }

    // ✅ Get products by category for a specific shop
    public List<ProductEntity> getProductsByCategory(Long shopId, String category) {
        return productRepository.findByShopIdAndCategory(shopId, category);
    }
}
