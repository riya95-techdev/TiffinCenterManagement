package com.example.tiffin_center_management.payload;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubscriptionDTO {

	private Long id;

    @NotNull(message = "Customer ID required")
    private Long customerId;

    private Long deliveryBoyId;

    @NotBlank(message = "Plan required")
    private String plan; // Monthly / Weekly

    private double price;

    private LocalDate startDate;
    private LocalDate endDate;

    private String status; // ACTIVE / CANCELLED / COMPLETED
	
}
