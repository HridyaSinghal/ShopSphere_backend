package com.example.SummerProject.Config;

//errpackage com.example.SummerProject.Config;

import com.example.SummerProject.Config.CommonSecurityBeans;
import com.example.SummerProject.Service.CustomUserDetailsService;
import com.example.SummerProject.Config.UserJwtFilter;
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
import org.springframework.http.HttpMethod;

@EnableWebSecurity
@Configuration
@Order(2) // Lower priority than ShopOwner
public class UserSecurityConfig {
@Autowired
private CommonSecurityBeans commonSecurityBeans;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserJwtFilter jwtFilter;


    @Bean(name = "userFilterChain")
    public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/auth/**")
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll() // adjust paths as needed
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(userAuthProvider())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean(name = "userApiFilterChain")
    public SecurityFilterChain userApiFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/user/**")
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Allow CORS preflight
                .requestMatchers("/api/user/auth/**").permitAll()
                .requestMatchers("/api/user/**").hasRole("USER")
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(userAuthProvider())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean(name = "publicApiFilterChain")
    public SecurityFilterChain publicApiFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/public/**")
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Allow CORS preflight
                .requestMatchers("/api/public/**").permitAll() // Allow all public endpoints
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider userAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(commonSecurityBeans.passwordEncoder());
        return provider;
    }

    @Bean(name = "userAuthenticationManager")
    public AuthenticationManager userAuthenticationManager() throws Exception {
        return new org.springframework.security.authentication.ProviderManager(userAuthProvider());
    }

}
