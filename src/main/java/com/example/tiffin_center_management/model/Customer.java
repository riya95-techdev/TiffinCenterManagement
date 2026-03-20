package com.example.tiffin_center_management.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name="customers")
@Data
public class Customer extends BaseUser{

	private String phone;

    private String address;

    private boolean active;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @ToString.Exclude 
    @EqualsAndHashCode.Exclude
    private List<Subscription> subscriptions;
	
}
