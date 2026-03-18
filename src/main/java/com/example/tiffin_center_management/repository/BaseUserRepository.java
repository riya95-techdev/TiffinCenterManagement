package com.example.tiffin_center_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiffin_center_management.model.BaseUser;

public interface BaseUserRepository extends JpaRepository<BaseUser, Long>{

	Optional<BaseUser> findByEmail(String email);

}
