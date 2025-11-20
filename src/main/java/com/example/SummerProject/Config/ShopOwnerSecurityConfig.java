package com.example.SummerProject.Config;

import com.example.SummerProject.Service.CustomShopOwnerDetailsService;
import com.example.SummerProject.Config.ShopOwnerJwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@Order(1) // Higher priority than UserSecurityConfig
public class ShopOwnerSecurityConfig {
    @Autowired
    private  CommonSecurityBeans commonSecurityBeans;


    @Autowired
    private CustomShopOwnerDetailsService customShopOwnerDetailsService;

    @Autowired
    private ShopOwnerJwtFilter shopOwnerJwtFilter;


    @Bean(name = "shopOwnerFilterChain")
    public SecurityFilterChain shopOwnerFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/shopowner/**")
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/shopowner/login", "/shopowner/signup").permitAll()
                .requestMatchers("/shopowner/**").hasRole("SHOPOWNER")
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(shopOwnerAuthProvider())
            .addFilterBefore(shopOwnerJwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider shopOwnerAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customShopOwnerDetailsService);
        provider.setPasswordEncoder(commonSecurityBeans.passwordEncoder());
        return provider;
    }

    @Bean(name = "shopOwnerAuthenticationManager")
    @org.springframework.context.annotation.Primary
    public AuthenticationManager shopOwnerAuthenticationManager() throws Exception {
        return new org.springframework.security.authentication.ProviderManager(shopOwnerAuthProvider());
    }

}
