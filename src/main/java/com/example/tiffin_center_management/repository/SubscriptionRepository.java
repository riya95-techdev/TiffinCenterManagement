package com.example.tiffin_center_management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tiffin_center_management.model.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{

	Page<Subscription> findByCustomerId(Long customerId, Pageable pageable);

    Page<Subscription> findByDeliveryBoyId(Long deliveryBoyId, Pageable pageable);
	
}
