package com.example.SummerProject.Controller;

import com.example.SummerProject.Config.CommonSecurityBeans;
import com.example.SummerProject.DTO.ShopAuthRequest;
import com.example.SummerProject.DTO.ShopAuthResponse;
import com.example.SummerProject.DTO.ShopOwnerDTO;
import com.example.SummerProject.Entity.ShopEntity;
import com.example.SummerProject.Entity.ShopOwnerEntity;
import com.example.SummerProject.Repository.ShopOwnerRepository;
import com.example.SummerProject.Repository.ShopsRepository;
import com.example.SummerProject.Service.CustomShopOwnerDetailsService;
import com.example.SummerProject.Util.ShopOwnerJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/shopowner")
public class ShopOwnerAuthController {

    @Autowired
    @Qualifier("shopOwnerAuthenticationManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomShopOwnerDetailsService customShopOwnerDetailsService;

    @Autowired
    private ShopOwnerJwtUtil shopOwnerJwtUtil;

    @Autowired
    private ShopOwnerRepository shopOwnerRepository;

    @Autowired
    private ShopsRepository shopRepository;

    @Autowired
     private PasswordEncoder passwordEncoder;



    // LOGIN API
    @PostMapping("/login")
    public ShopAuthResponse login(@RequestBody ShopAuthRequest shopOwnerDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(shopOwnerDTO.getEmail(), shopOwnerDTO.getPassword()));

        UserDetails userDetails = customShopOwnerDetailsService.loadUserByUsername(shopOwnerDTO.getEmail());

        ShopOwnerEntity shopOwner = shopOwnerRepository.findByEmailId(shopOwnerDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Shop owner not found"));

        String token = shopOwnerJwtUtil.generateToken(userDetails,
                shopOwner.getName(),
                shopOwner.getShopName(),
                shopOwner.getAddress(),
                shopOwner.getShop().getId());

        // Return all relevant details in the response
        return new ShopAuthResponse(
            token,
            shopOwner.getName(),
            shopOwner.getShopName(),
            shopOwner.getEmailId(),
            shopOwner.getShop().getId()
        );
    }

    //  SIGNUP API

    @PostMapping("/signup")
    public ShopOwnerDTO signup(@RequestBody ShopOwnerEntity shopOwnerEntity) {

        if (shopOwnerRepository.findByEmailId(shopOwnerEntity.getEmailId()).isPresent()) {
            throw new RuntimeException("Email already exists!");
        }

        // Encode password
        shopOwnerEntity.setPassword(passwordEncoder.encode(shopOwnerEntity.getPassword()));
        shopOwnerEntity.setRoles(List.of("ROLE_SHOPOWNER"));

        // Save owner first to get generated ID
        ShopOwnerEntity savedOwner = shopOwnerRepository.save(shopOwnerEntity);

        // Create shop entity
        ShopEntity shop = new ShopEntity();
        shop.setShopOwner(savedOwner);
        shop.setShopOwnerName(savedOwner.getName());
        shop.setShopName(savedOwner.getShopName());
        shop.setTotalEarning(0L);
        shop.setRetailerEmail(savedOwner.getRetailerEmail());

        // Format shop name for URL (SEO-friendly)
        String shopNameUrl = savedOwner.getShopName()
                .trim().toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")    // remove special chars
                .replaceAll("\\s+", "-");          // replace spaces with -

        String storeLink = "http://localhost:8080/" + shopNameUrl + "/" + savedOwner.getId();

        shop.setLink(storeLink);

        // VERY IMPORTANT: Set shop in owner before saving again
        savedOwner.setStoreLink(storeLink);   // Optional: if you also store it in owner entity
        savedOwner.setShop(shop);             // Set shop back to owner

        shopRepository.save(shop);
        shopOwnerRepository.save(savedOwner);  // save updated owner with shop

        return convertToDTO(savedOwner);
    }


    // Utility: convert Entity to DTO
    private ShopOwnerDTO convertToDTO(ShopOwnerEntity user) {
        ShopOwnerDTO dto = new ShopOwnerDTO();
        dto.setId(user.getId());
        dto.setEmailId(user.getEmailId());
        dto.setName(user.getName());
        dto.setShopName(user.getShopName());
        dto.setPhoneNo(user.getPhoneNo());
        dto.setAddress(user.getAddress());
        if (user.getShop() != null) {
            dto.setStoreLink(user.getShop().getLink());
        }
        return dto;
    }



}
