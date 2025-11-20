package com.example.SummerProject.Controller;

import com.example.SummerProject.DTO.UserAuthRequest;
import com.example.SummerProject.DTO.UserAuthResponse;
import com.example.SummerProject.Entity.UserEntity;
import com.example.SummerProject.Repository.UserRepository;
import com.example.SummerProject.Service.CustomUserDetailsService;
import com.example.SummerProject.Util.UserJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired(required = true)
    @org.springframework.beans.factory.annotation.Qualifier("userAuthenticationManager")
    private AuthenticationManager authManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserJwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserEntity user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepo.save(user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAuthRequest authReq) {
        try {
            System.out.println("Login attempt: " + authReq.getEmailId());
            UserEntity user = userRepo.findByEmailId(authReq.getEmailId()).orElse(null);
            System.out.println("User found: " + (user != null));
            if (user != null) {
                System.out.println("User password (hashed): " + user.getPassword());
            }
            authManager.authenticate(new UsernamePasswordAuthenticationToken(authReq.getEmailId(), authReq.getPassword()));
            user = userDetailsService.getUser(authReq.getEmailId());
            String token = jwtUtil.generateToken(user);
            System.out.println("Authentication successful for: " + authReq.getEmailId());
            return ResponseEntity.ok(new UserAuthResponse(
                    token,
                    user.getName(),
                    user.getEmailId(),
                    user.getRoles().toString(),
                    user.getAddress()
            ));
        } catch (org.springframework.security.core.AuthenticationException ex) {
            // Log the failed login attempt
            System.out.println("Login failed for user: " + authReq.getEmailId() + " - " + ex.getMessage());
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}

