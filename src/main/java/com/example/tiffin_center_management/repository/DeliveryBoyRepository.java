package com.example.tiffin_center_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tiffin_center_management.model.BaseUser;
import com.example.tiffin_center_management.model.DeliveryBoy;

@Repository
public interface DeliveryBoyRepository extends JpaRepository<DeliveryBoy, Long>{

	Optional<? extends BaseUser> findByEmail(String email);

}
