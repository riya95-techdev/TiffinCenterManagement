package com.example.tiffin_center_management.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiffin_center_management.payload.CustomerDTO;
import com.example.tiffin_center_management.payload.DeliveryBoyDTO;
import com.example.tiffin_center_management.payload.SubscriptionDTO;
import com.example.tiffin_center_management.service.CustomerService;
import com.example.tiffin_center_management.service.DeliveryBoyService;
import com.example.tiffin_center_management.service.SubscriptionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

	private final CustomerService customerService;
    private final DeliveryBoyService deliveryService;
    private final SubscriptionService subscriptionService;

    // 🔍 ALL DATA
    @GetMapping("/customers")
    public Page<CustomerDTO> getAllCustomers(
    		@RequestParam(defaultValue = "0") int page,
    	    @RequestParam(defaultValue = "5") int size,
    	    @RequestParam(defaultValue = "id") String sortBy,
    	    @RequestParam(defaultValue = "asc") String sortDir
    		) {
        return customerService.getAll(page, size,sortBy,sortDir);
    }

    @GetMapping("/delivery-boys")
    public Page<DeliveryBoyDTO> getAllDelivery(
    		@RequestParam(defaultValue = "0") int page,
    	    @RequestParam(defaultValue = "5") int size,
    	    @RequestParam(defaultValue = "id") String sortBy,
    	    @RequestParam(defaultValue = "asc") String sortDir
    		) {
        return deliveryService.getAll(page, size,sortBy, sortDir);
    }

    @GetMapping("/subscriptions")
    public Page<SubscriptionDTO> getAllSubs(
    		@RequestParam(defaultValue = "0") int page,
    	    @RequestParam(defaultValue = "5") int size,
    	    @RequestParam(defaultValue = "id") String sortBy,
    	    @RequestParam(defaultValue = "asc") String sortDir
    		) {
        return subscriptionService.getAll(page, size,sortBy,sortDir);
    }

    // 🔎 SINGLE FETCH
    @GetMapping("/customer/{id}")
    public CustomerDTO getCustomer(@PathVariable Long id) {
        return customerService.getById(id);
    }

    @GetMapping("/delivery/{id}")
    public DeliveryBoyDTO getDelivery(@PathVariable Long id) {
        return deliveryService.getById(id);
    }

    @GetMapping("/subscription/{id}")
    public SubscriptionDTO getSub(@PathVariable Long id) {
        return subscriptionService.getById(id);
    }

    // ❌ DELETE
    @DeleteMapping("/customer/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return "Customer deleted";
    }

    @DeleteMapping("/delivery/{id}")
    public String deleteDelivery(@PathVariable Long id) {
        deliveryService.delete(id);
        return "DeliveryBoy deleted";
    }

    @DeleteMapping("/subscription/{id}")
    public String deleteSub(@PathVariable Long id) {
        subscriptionService.delete(id);
        return "Subscription deleted";
    }
	
}
