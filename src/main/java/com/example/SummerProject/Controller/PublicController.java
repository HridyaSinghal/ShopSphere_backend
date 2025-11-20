package com.example.SummerProject.Controller;

import com.example.SummerProject.DTO.ShopDTO;
import com.example.SummerProject.Service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.SummerProject.Entity.ProductEntity;
import com.example.SummerProject.Service.ProductService;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173", "http://localhost:5174"})
public class PublicController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductService productService;

    // Public endpoint to show all shops (no authentication required)
    @GetMapping("/all-shops")
    public ResponseEntity<List<ShopDTO>> getAllShops() {
        List<ShopDTO> shops = shopService.getAllShops();
        return ResponseEntity.ok(shops);
    }

    // Public endpoint to get all products of a specific shop
    @GetMapping("/shop/{shopId}/products")
    public ResponseEntity<List<ProductEntity>> getShopProducts(@PathVariable Long shopId) {
        List<ProductEntity> products = productService.getAllProductsByShopId(shopId);
        return ResponseEntity.ok(products);
    }

    // Public endpoint to update product quantity after purchase
    @PutMapping("/purchase-product")
    public ResponseEntity<String> purchaseProduct(@RequestBody ProductEntity productEntity) {
        // Use the shop id from the product entity
        Long shopId = productEntity.getShop() != null ? productEntity.getShop().getShop_id() : null;
        if (shopId == null) return ResponseEntity.badRequest().body("Shop ID missing in product");
        return ResponseEntity.ok(productService.updateProduct(shopId, productEntity));
    }
} 