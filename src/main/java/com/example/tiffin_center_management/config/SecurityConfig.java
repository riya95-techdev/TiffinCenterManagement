package com.example.tiffin_center_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.tiffin_center_management.security.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	 private final JwtFilter jwtFilter;

	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	        http
	            .csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(auth -> auth

	                // Public APIs
	                .requestMatchers("/api/auth/**").permitAll()

	                // Role-based APIs
	                .requestMatchers("/api/admin/**").hasRole("ADMIN")
	                .requestMatchers("/api/customer/**").hasRole("CUSTOMER")
	                .requestMatchers("/api/deliveryboy/**").hasRole("DELIVERY")

	                // All others secured
	                .anyRequest().authenticated()
	            )

	         // 🔥 VERY IMPORTANT (401 fix)
	            .exceptionHandling(ex -> ex
	                .authenticationEntryPoint((req, res, e) -> {
	                    res.sendError(401, "Unauthorized - Please Login");
	                })
	                .accessDeniedHandler((req, res, e) -> {
	                    res.sendError(403, "Forbidden - Access Denied");
	                })
	            )
	            
	            // JWT Filter
	            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	        return http.build();
	    }

	    // Password Encoder (IMPORTANT)
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	
}
