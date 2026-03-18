package com.example.tiffin_center_management.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

	@Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password required")
    private String password;
	
}
