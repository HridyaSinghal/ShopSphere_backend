package com.example.SummerProject.Service;

import com.example.SummerProject.Entity.ShopOwnerEntity;
import com.example.SummerProject.Repository.ShopOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomShopOwnerDetailsService implements UserDetailsService {

    @Autowired
    private ShopOwnerRepository shopOwnerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ShopOwnerEntity owner = shopOwnerRepository.findByEmailId(email)
                .orElseThrow(() -> new UsernameNotFoundException("Shop Owner not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(
                owner.getEmailId(),
                owner.getPassword(),
                owner.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role))
                    .collect(Collectors.toList())
        );
    }
}
