//package com.example.SummerProject.Controller;
//
//import com.example.SummerProject.DTO.ShopOwnerDTO;
//import com.example.SummerProject.Entity.ProductEntity;
//import com.example.SummerProject.Entity.ShopOwnerEntity;
//import com.example.SummerProject.Service.ProductService;
////import com.example.SummerProject.Service.ShopOwnerService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/shop")
//public class ShopController {
//
////    @Autowired
////    private ShopOwnerService shopOwnerService;
//
//    @Autowired
//    private ProductService productService;
//
//
//  // @PostMapping(value = "/create-product", consumes = {"multipart/form-data"})
//    @PostMapping(value = "/create-product")
//    public ResponseEntity<String> createProduct(@RequestBody ProductEntity productEntity) {
//        return ResponseEntity.ok(productService.createProduct(productEntity));
//    }
//
//    //delete product
//    @DeleteMapping("/delete-product/{shop_id}/{pro_id}")
//    public ResponseEntity<String> deleteProductFromShop(@PathVariable("shop_id") Long shopId,
//                                                        @PathVariable("pro_id") Long productId) {
//        return ResponseEntity.ok(productService.deleteProductFromShop(shopId, productId));
//    }
//
//    //update product
//    @PutMapping("/update-product")
//    public ResponseEntity<String> updateProduct(@RequestBody ProductEntity productEntity){
//        return ResponseEntity.ok(productService.updateProduct(productEntity));
//    }
//
//
//    @GetMapping("/shop/{shopName}/{shopId}")
//    public ResponseEntity<List<ProductEntity>> getAllProducts(@PathVariable String shopName, @PathVariable Long shopId) {
//        return ResponseEntity.ok(productService.getAllProductsByShopId(shopId));
//    }
//
//
//}



package com.example.SummerProject.Controller;

import com.example.SummerProject.Entity.ProductEntity;
import com.example.SummerProject.Service.ProductService;
import com.example.SummerProject.Util.ShopOwnerJwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ShopOwnerJwtUtil jwtUtil;

    // Create product (done)
    @PostMapping("/create-product")
    public ResponseEntity<String> createProduct(@RequestBody ProductEntity productEntity, HttpServletRequest request) {
        Long shopId = extractShopIdFromToken(request);
        return ResponseEntity.ok(productService.createProduct(shopId,productEntity));
    }

    // Delete product (done)
    @DeleteMapping("/delete-product/{pro_id}")
    public ResponseEntity<String> deleteProductFromShop(@PathVariable("pro_id") Long productId, HttpServletRequest request) {
        Long shopId = extractShopIdFromToken(request);
        return ResponseEntity.ok(productService.deleteProductFromShop(shopId, productId));
    }

    // Update product (done)
    @PutMapping("/update-product")
    public ResponseEntity<String> updateProduct(@RequestBody ProductEntity productEntity, HttpServletRequest request) {
        Long shopId = extractShopIdFromToken(request);

        return ResponseEntity.ok(productService.updateProduct(shopId,productEntity));
    }

    // Get all products for shop specific shop (done)
    @GetMapping("/products")
    public ResponseEntity<List<ProductEntity>> getAllProducts(HttpServletRequest request) {
        Long shopId = extractShopIdFromToken(request);
        return ResponseEntity.ok(productService.getAllProductsByShopId(shopId));
    }




    // ========== JWT Extract Helper ==========
    private Long extractShopIdFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            return jwtUtil.extractClaim(jwt, claims -> claims.get("shopId", Long.class));
        }
        throw new RuntimeException("Invalid JWT Token: shopId not found");
    }

    private String extractShopNameFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            return jwtUtil.extractClaim(jwt, claims -> claims.get("shopName", String.class));
        }
        throw new RuntimeException("Invalid JWT Token: shopName not found");
    }
}
