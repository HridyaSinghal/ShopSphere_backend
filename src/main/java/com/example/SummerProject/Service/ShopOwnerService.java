//package com.example.SummerProject.Service;
//
//import com.example.SummerProject.DTO.ShopOwnerDTO;
//import com.example.SummerProject.Entity.ShopEntity;
//import com.example.SummerProject.Entity.ShopOwnerEntity;
//import com.example.SummerProject.Entity.UserEntity;
//import com.example.SummerProject.Repository.ShopOwnerRepository;
//import com.example.SummerProject.Repository.ShopsRepository;
//import com.example.SummerProject.security.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ShopOwnerService {
//
//@Autowired
//    private ShopOwnerRepository shopOwnerRepository;
//
//@Autowired
//private ShopsRepository shopsRepository;
//
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    public ShopOwnerDTO signup(ShopOwnerEntity shopOwnerEntity) {
//
//        if (shopOwnerRepository.findByEmailId(shopOwnerEntity.getEmailId()).isPresent()) {
//            throw new RuntimeException("Email already exists!");
//        }
//
//        shopOwnerEntity.setPassword(passwordEncoder.encode(shopOwnerEntity.getPassword()));
//        shopOwnerEntity.setRoles(List.of("ROLE_SHOPOWNER")); // ✅ Correct
//
//        // Save owner first
//        ShopOwnerEntity savedOwner = shopOwnerRepository.save(shopOwnerEntity);
//
//        // Create and link shop
//        ShopEntity shop = new ShopEntity();
//        shop.setShopOwner(savedOwner);
//        shop.setShopOwnerName(savedOwner.getName());
//        shop.setShopName(savedOwner.getShopName());
//        shop.setTotalEarning(0L);
//
//        String shopNameUrl = savedOwner.getShopName().replaceAll("\\s+", "");
//        String storeLink = "http://localhost:8080/shop/" + shopNameUrl + "/" + savedOwner.getId();
//        savedOwner.setStoreLink(storeLink);
//        shop.setLink(storeLink);
//
//        shopsRepository.save(shop);
//        shopOwnerRepository.save(savedOwner);
//
//        return convertToShopOwnerDTO(savedOwner);
//    }
//
//    public ShopOwnerDTO getShopOwnerByEmail(String email) {
//        ShopOwnerEntity owner = shopOwnerRepository.findByEmailId(email)
//                .orElseThrow(() -> new RuntimeException("Shop owner not found"));
//        return convertToShopOwnerDTO(owner);
//    }
//
//    public String login(String emailId, String password) {
//        System.out.println("Login attempt for email: " + emailId);
//        if (emailId == null || emailId.trim().isEmpty()) {
//            throw new RuntimeException("Email must not be empty");
//        }
//        if (password == null || password.trim().isEmpty()) {
//            throw new RuntimeException("Password must not be empty");
//        }
//
//        ShopOwnerEntity user = shopOwnerRepository.findByEmailId(emailId.trim())
//                .orElseThrow(() -> {
//                    System.out.println("User not found for email: " + emailId);
//                    return new RuntimeException("User not found");
//                });
//
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            System.out.println("Invalid password for email: " + emailId);
//            throw new RuntimeException("Invalid credentials");
//        }
//
//        // Load UserDetails to include roles in token
//        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
//                user.getEmailId(),
//                user.getPassword(),
//                user.getRoles().stream()
//                        .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority(role))
//                        .toList()
//        );
//
//        return jwtUtil.generateToken(userDetails);
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
////    public ShopOwnerDTO signUpDetails(ShopOwnerEntity shopOwnerEntity) {
////
////        ShopOwnerEntity savedOwner = shopOwnerRepository.save(shopOwnerEntity);
////
////        ShopEntity shop = new ShopEntity();
////        shop.setShopOwner(savedOwner);
////        shop.setShopOwnerName(savedOwner.getName());
////        shop.setShopName(savedOwner.getShopName());
////        shop.setTotalEarning(0L); // or any default value
////
////
////        String shopNameUrl = savedOwner.getShopName().replaceAll("\\s+", "");
////        String storeLink = "http://localhost:8080/shop/" + shopNameUrl + "/" + savedOwner.getId();
////        savedOwner.setStoreLink(storeLink);
////
////        shop.setLink(storeLink);
////        shopsRepository.save(shop);
////        shopOwnerRepository.save(savedOwner);
////
////        return convertToShopOwnerDTO(savedOwner);
////
////    }
////
////
////
//
//    private ShopOwnerDTO convertToShopOwnerDTO(ShopOwnerEntity owner) {
//
//        ShopOwnerDTO dto = new ShopOwnerDTO();
//        dto.setId(owner.getId());
//        dto.setShopName(owner.getShopName());
//        dto.setEmailId(owner.getEmailId());
//        dto.setAddress(owner.getAddress());
//        dto.setStoreLink(owner.getStoreLink());
//        dto.setName(owner.getName());
//        dto.setPhoneNo(owner.getPhoneNo());
//
//        return dto;
//    }
//}
