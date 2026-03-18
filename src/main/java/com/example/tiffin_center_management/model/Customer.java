package com.example.tiffin_center_management.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="customers")
@Data
public class Customer extends BaseUser{

	private String phone;

    private String address;

    private boolean active;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Subscription> subscriptions;
	
}
