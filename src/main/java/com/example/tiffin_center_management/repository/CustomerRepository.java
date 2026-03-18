package com.example.tiffin_center_management.repository;

import java.util.Optional;

import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import com.example.tiffin_center_management.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	Optional<Customer> findByEmail(String email);

	Streamable<Order> findByNameContaining(String keyword);
	
}
