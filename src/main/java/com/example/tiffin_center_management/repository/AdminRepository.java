package com.example.tiffin_center_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tiffin_center_management.model.Admin;
import com.example.tiffin_center_management.model.BaseUser;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>{

	Optional<? extends BaseUser> findByEmail(String email);

}
