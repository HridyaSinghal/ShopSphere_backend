package com.example.SummerProject.Controller;

import com.example.SummerProject.DTO.AdminCustomerResponse;
import com.example.SummerProject.DTO.AdminShopResponse;
import com.example.SummerProject.Entity.AdminEntity;
import com.example.SummerProject.Entity.UserEntity;
import com.example.SummerProject.Service.AdminService;
import com.example.SummerProject.DTO.AdminLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

@Autowired
private AdminService adminService;


@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody AdminEntity adminEntity){

    adminService.saveDetail(adminEntity);

//    // Fetch customer and shop details
//    List<AdminCustomerResponse> customers = adminService.getDetail();
//    List<AdminShopResponse> shops = adminService.getShopDetail();
//
//    // Create combined response
//    AdminLoginResponse response = new AdminLoginResponse(customers, shops);

    return ResponseEntity.ok("Admin LOGIN");
}

@GetMapping("/customer-info")
    public ResponseEntity<List<AdminCustomerResponse>> getCustomerDetail(){

    List<AdminCustomerResponse> detail = adminService.getDetail();

    return ResponseEntity.ok(detail);

}


    @GetMapping("/ShopOwner-info")
    public ResponseEntity<List<AdminShopResponse>> getShopOwnerDetail(){

        List<AdminShopResponse> detail = adminService.getShopDetail();

        return ResponseEntity.ok(detail);

    }




}
