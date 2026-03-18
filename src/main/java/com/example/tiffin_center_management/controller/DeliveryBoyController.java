package com.example.tiffin_center_management.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiffin_center_management.payload.DeliveryBoyDTO;
import com.example.tiffin_center_management.payload.SubscriptionDTO;
import com.example.tiffin_center_management.service.DeliveryBoyService;
import com.example.tiffin_center_management.service.SubscriptionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deliveryboy")
public class DeliveryBoyController {

	private final DeliveryBoyService service;

    @PostMapping
    public DeliveryBoyDTO create(@RequestBody DeliveryBoyDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public Page<DeliveryBoyDTO> getAll(
    		@RequestParam(defaultValue = "0") int page,
    	    @RequestParam(defaultValue = "5") int size,
    	    @RequestParam(defaultValue = "id") String sortBy,
    	    @RequestParam(defaultValue = "asc") String sortDir
    		) {
        return service.getAll(page, size,sortBy,sortDir);
    }

    @GetMapping("/{id}")
    public DeliveryBoyDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public DeliveryBoyDTO update(@PathVariable Long id,
                                 @RequestBody DeliveryBoyDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted";
    }
	
}
