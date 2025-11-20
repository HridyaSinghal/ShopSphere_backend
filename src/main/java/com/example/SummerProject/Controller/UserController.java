package com.example.SummerProject.Controller;

import com.example.SummerProject.DTO.ShopDTO;
import com.example.SummerProject.Entity.ProductEntity;
import com.example.SummerProject.Service.ProductService;
import com.example.SummerProject.Service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductService productService;


    // show all shops (done)
    @GetMapping("/all-shops")
    public ResponseEntity<List<ShopDTO>> getAllShops() {
        List<ShopDTO> shops = shopService.getAllShops();
        return ResponseEntity.ok(shops);
    }

//get specific product from a particular shop (done)

    @GetMapping("/shop/{shopId}/products/search")
    public ResponseEntity<List<ProductEntity>> getSpecificProduct(
            @PathVariable Long shopId,
            @RequestParam("product") String productName) {

        List<ProductEntity> products = productService.getSpecificProduct(shopId, productName);
        return ResponseEntity.ok(products);
    }



    // Get all products of a specific shop by shop id (and optionally shop name) (done)
    @GetMapping("/{shopName}/{shopId}")
    public ResponseEntity<List<ProductEntity>> getAllProductsByShopId(
            @PathVariable String shopName,
            @PathVariable Long shopId) {
        // You can use shopName for validation or just ignore it if not needed
        return ResponseEntity.ok(productService.getAllProductsByShopId(shopId));
    }




}
