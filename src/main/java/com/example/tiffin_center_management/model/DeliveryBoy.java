package com.example.tiffin_center_management.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="deliveryboys")
@Data
@EqualsAndHashCode(callSuper = true) // Lombok fix for inheritance
@DiscriminatorValue("DELIVERY")
public class DeliveryBoy extends BaseUser{

	private String phone;

    private String address;

    private boolean active;
    
    private String vehicleNumber;

    private boolean available;

    @OneToMany(mappedBy = "deliveryBoy", cascade = CascadeType.ALL)
    private List<Subscription> subscriptions;
	
}
