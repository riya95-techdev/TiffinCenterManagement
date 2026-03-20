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
import com.example.tiffin_center_management.repository.BaseUserRepository;
import com.example.tiffin_center_management.repository.CustomerRepository;
import com.example.tiffin_center_management.repository.DeliveryBoyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService{

	private final BaseUserRepository baseUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Sirf BaseUser se pura data nikal jayega (Clean approach)
        BaseUser user = baseUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Yahan "ROLE_" prefix lagana compulsory hai taaki SecurityConfig ka hasRole() chale
        String roleWithPrefix = "ROLE_" + user.getRole(); 

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(roleWithPrefix))
        );
    }
	
	
//	private final AdminRepository adminRepository;
//    private final CustomerRepository customerRepository;
//    private final DeliveryBoyRepository deliveryRepository;
//
//    private final BaseUserRepository baseUserRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        // Teen repositories ki jagah sirf BaseUser use karein kyunki email unique hai
//        BaseUser user = baseUserRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//
//        // IMPORTANT: Role ke saath "ROLE_" prefix lagana zaroori hai agar hasRole() use karna hai
//        String roleWithPrefix = "ROLE_" + user.getRole(); 
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(),
//                user.getPassword(),
//                List.of(new SimpleGrantedAuthority(roleWithPrefix))
//        );
//    }
//    
////    @Override
////    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
////
////        // 1. Try Admin
////        Optional<? extends BaseUser> admin = adminRepository.findByEmail(email);
////        if (admin.isPresent()) {
////            return buildUser(admin.get(), "ADMIN");
////        }
////
////        // 2. Try Customer
////        Optional<? extends BaseUser> customer = customerRepository.findByEmail(email);
////        if (customer.isPresent()) {
////            return buildUser(customer.get(), "CUSTOMER");
////        }
////
////        // 3. Try DeliveryBoy
////        Optional<? extends BaseUser> delivery = deliveryRepository.findByEmail(email);
////        if (delivery.isPresent()) {
////            return buildUser(delivery.get(), "DELIVERY");
////        }
////
////        throw new UsernameNotFoundException("User not found with email: " + email);
////    }
//
//    private UserDetails buildUser(BaseUser user, String role) {
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(),
//                user.getPassword(),
//                List.of(new SimpleGrantedAuthority(role))
//        );
//    }
//	
}
