package com.example.tiffin_center_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="admin")
@Data
public class Admin extends BaseUser{

}
