package com.example.tiffin_center_management.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DeliveryBoyDTO {

	private Long id;

    @NotBlank(message = "Name required")
    private String name;

    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone")
    private String phone;

    private String vehicleNumber;

    private boolean available;
	
}
