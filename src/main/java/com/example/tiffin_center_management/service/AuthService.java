package com.example.tiffin_center_management.service;

import com.example.tiffin_center_management.payload.LoginRequest;
import com.example.tiffin_center_management.payload.SignupRequest;

public interface AuthService {

	void signup(SignupRequest request);
	String login(LoginRequest request);
	
}
