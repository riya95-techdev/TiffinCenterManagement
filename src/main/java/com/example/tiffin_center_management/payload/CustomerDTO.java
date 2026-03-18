package com.example.tiffin_center_management.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerDTO {
	private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
    private String phone;

    private String address;

    private boolean active;
}
	
	
