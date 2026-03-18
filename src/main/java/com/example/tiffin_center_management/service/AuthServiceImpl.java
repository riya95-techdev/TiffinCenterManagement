package com.example.tiffin_center_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tiffin_center_management.exception.EmailAlreadyExistsException;
import com.example.tiffin_center_management.model.Admin;
import com.example.tiffin_center_management.model.BaseUser;
import com.example.tiffin_center_management.model.Customer;
import com.example.tiffin_center_management.model.DeliveryBoy;
import com.example.tiffin_center_management.payload.LoginRequest;
import com.example.tiffin_center_management.payload.SignupRequest;
import com.example.tiffin_center_management.repository.BaseUserRepository;
import com.example.tiffin_center_management.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

	@Autowired
	private final BaseUserRepository userRepo;
	@Autowired
    private final PasswordEncoder encoder;
	@Autowired
    private final JwtUtil jwtUtil;

    @Override
    public void signup(SignupRequest req) {

    	if (userRepo.findByEmail(req.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already registered");
        }
    	
        BaseUser user;

        switch (req.getRole()) {
            case "ADMIN":
                user = new Admin();
                break;
            case "DELIVERY":
                user = new DeliveryBoy();
                break;
            default:
                user = new Customer();
        }

        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRole(req.getRole());

        userRepo.save(user);
    }

    @Override
    public String login(LoginRequest
    		req) {

        BaseUser user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
	
}
