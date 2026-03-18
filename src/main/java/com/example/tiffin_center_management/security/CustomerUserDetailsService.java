package com.example.tiffin_center_management.security;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.tiffin_center_management.model.BaseUser;
import com.example.tiffin_center_management.repository.AdminRepository;
import com.example.tiffin_center_management.repository.CustomerRepository;
import com.example.tiffin_center_management.repository.DeliveryBoyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService{

	private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final DeliveryBoyRepository deliveryRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 1. Try Admin
        Optional<? extends BaseUser> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) {
            return buildUser(admin.get(), "ROLE_ADMIN");
        }

        // 2. Try Customer
        Optional<? extends BaseUser> customer = customerRepository.findByEmail(email);
        if (customer.isPresent()) {
            return buildUser(customer.get(), "ROLE_CUSTOMER");
        }

        // 3. Try DeliveryBoy
        Optional<? extends BaseUser> delivery = deliveryRepository.findByEmail(email);
        if (delivery.isPresent()) {
            return buildUser(delivery.get(), "ROLE_DELIVERY");
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }

    private UserDetails buildUser(BaseUser user, String role) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(role))
        );
    }
	
}
