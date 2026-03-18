package com.example.tiffin_center_management.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignupRequest {

	 @NotBlank
	    private String name;

	    @Email
	    private String email;

	    @NotBlank
	    private String password;

	    private String role; // ADMIN / CUSTOMER / DELIVERY

	    @Pattern(regexp = "^[0-9]{10}$")
	    private String phone;
	
}
