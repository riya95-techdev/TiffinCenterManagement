package com.example.tiffin_center_management.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name="subscriptions")
@Data
public class Subscription {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Relationships
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "delivery_boy_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private DeliveryBoy deliveryBoy;

    // 📅 Business fields
    private String plan; // Monthly / Weekly

    private double price;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status; // ACTIVE / CANCELLED / COMPLETED
	
}
