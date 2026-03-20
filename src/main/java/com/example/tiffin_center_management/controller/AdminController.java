package com.example.tiffin_center_management.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.tiffin_center_management.payload.ApiResponse;
import com.example.tiffin_center_management.payload.CustomerDTO;
import com.example.tiffin_center_management.payload.DeliveryBoyDTO;
import com.example.tiffin_center_management.payload.SubscriptionDTO;
import com.example.tiffin_center_management.service.CustomerService;
import com.example.tiffin_center_management.service.DeliveryBoyService;
import com.example.tiffin_center_management.service.SubscriptionService;
import com.example.tiffin_center_management.service.TiffinScheduler;

import lombok.RequiredArgsConstructor;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final CustomerService customerService;
    private final DeliveryBoyService deliveryService;
    private final SubscriptionService subscriptionService;
    private final TiffinScheduler tiffinScheduler;
    
    // 🔍 FETCH ALL DATA (GET)
    @GetMapping("/customers")
    public Page<CustomerDTO> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return customerService.getAll(page, size, sortBy, sortDir);
    }

    @GetMapping("/delivery-boys")
    public Page<DeliveryBoyDTO> getAllDelivery(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return deliveryService.getAll(page, size, sortBy, sortDir);
    }

    @GetMapping("/subscriptions")
    public Page<SubscriptionDTO> getAllSubs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return subscriptionService.getAll(page, size, sortBy, sortDir);
    }

    // 🔎 ASSIGN DELIVERY BOY (PUT)
    // Ye line dhyan se dekho Postman me yahi URL hona chahiye
//    @PutMapping("/subscriptions/{subId}/assign-delivery/{deliveryBoyId}")
//    public ResponseEntity<ApiResponse> assignDeliveryBoy(
//            @PathVariable Long subId, 
//            @PathVariable Long deliveryBoyId) {
//        
//        subscriptionService.assignDeliveryBoy(subId, deliveryBoyId);
//        return ResponseEntity.ok(new ApiResponse("Delivery Boy assigned successfully", null));
//    }

    // 🗑️ DELETE METHODS (DELETE)
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable Long id) {
        customerService.delete(id); 
        return ResponseEntity.ok(new ApiResponse("Customer deleted successfully", null));
    }

    @PutMapping("/customer/{id}/activate")
    public ResponseEntity<ApiResponse> activateCustomer(@PathVariable Long id) {
        customerService.changeStatus(id, true);
        return ResponseEntity.ok(new ApiResponse("Customer Activated", null));
    }

    // 2. Assignment
    @PutMapping("/subscriptions/{subId}/assign-delivery/{dbId}")
    public ResponseEntity<ApiResponse> assign(@PathVariable Long subId, @PathVariable Long dbId) {
        subscriptionService.assignDeliveryBoy(subId, dbId);
        return ResponseEntity.ok(new ApiResponse("Assigned & Status is ACTIVE", null));
    }
    
 // Emergency ya Testing ke liye manually reset karne ka endpoint
    @PutMapping("/subscriptions/reset-daily-status")
    public ResponseEntity<ApiResponse> manualReset() {
        tiffinScheduler.resetDailyTiffinStatus(); // Scheduler ka method call karein
        return ResponseEntity.ok(new ApiResponse("All subscriptions reset to ACTIVE for today!", null));
    }
}



//package com.example.tiffin_center_management.controller;
//
//import org.springframework.data.domain.Page;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.tiffin_center_management.payload.ApiResponse;
//import com.example.tiffin_center_management.payload.CustomerDTO;
//import com.example.tiffin_center_management.payload.DeliveryBoyDTO;
//import com.example.tiffin_center_management.payload.SubscriptionDTO;
//import com.example.tiffin_center_management.service.CustomerService;
//import com.example.tiffin_center_management.service.DeliveryBoyService;
//import com.example.tiffin_center_management.service.SubscriptionService;
//
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/admin")
//public class AdminController {
//
//	private final CustomerService customerService;
//    private final DeliveryBoyService deliveryService;
//    private final SubscriptionService subscriptionService;
//
//    // 🔍 ALL DATA
//    @GetMapping("/customers")
//    public Page<CustomerDTO> getAllCustomers(
//    		@RequestParam(defaultValue = "0") int page,
//    	    @RequestParam(defaultValue = "5") int size,
//    	    @RequestParam(defaultValue = "id") String sortBy,
//    	    @RequestParam(defaultValue = "asc") String sortDir
//    		) {
//        return customerService.getAll(page, size,sortBy,sortDir);
//    }
//
//    @GetMapping("/delivery-boys")
//    public Page<DeliveryBoyDTO> getAllDelivery(
//    		@RequestParam(defaultValue = "0") int page,
//    	    @RequestParam(defaultValue = "5") int size,
//    	    @RequestParam(defaultValue = "id") String sortBy,
//    	    @RequestParam(defaultValue = "asc") String sortDir
//    		) {
//        return deliveryService.getAll(page, size,sortBy, sortDir);
//    }
//
//    @GetMapping("/subscriptions")
//    public Page<SubscriptionDTO> getAllSubs(
//    		@RequestParam(defaultValue = "0") int page,
//    	    @RequestParam(defaultValue = "5") int size,
//    	    @RequestParam(defaultValue = "id") String sortBy,
//    	    @RequestParam(defaultValue = "asc") String sortDir
//    		) {
//        return subscriptionService.getAll(page, size,sortBy,sortDir);
//    }
//
//    // 🔎 SINGLE FETCH
//    @GetMapping("/customer/{id}")
//    public CustomerDTO getCustomer(@PathVariable Long id) {
//        return customerService.getById(id);
//    }
//
//    @GetMapping("/delivery/{id}")
//    public DeliveryBoyDTO getDelivery(@PathVariable Long id) {
//        return deliveryService.getById(id);
//    }
//
//    @GetMapping("/subscription/{id}")
//    public SubscriptionDTO getSub(@PathVariable Long id) {
//        return subscriptionService.getById(id);
//    }
//
//    @DeleteMapping("/customer/{id}")
//    public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable Long id) {
//        // Service method call karein jo exception throw kare agar ID na ho
//        customerService.delete(id); 
//        
//        // Agar delete() fail hota hai toh niche wali line execute hi nahi hogi
//        return ResponseEntity.ok(new ApiResponse("Customer deleted successfully", null));
//    }
//
//    @DeleteMapping("/delivery/{id}")
//    public ResponseEntity<ApiResponse> deleteDeliveryBoy(@PathVariable Long id) {
//        deliveryService.delete(id);
//        return ResponseEntity.ok(new ApiResponse("Delivery Boy deleted successfully", null));
//    }
//
//    @DeleteMapping("/subscription/{id}")
//    public ResponseEntity<ApiResponse> deleteSubscription(@PathVariable Long id) {
//        subscriptionService.delete(id);
//        return ResponseEntity.ok(new ApiResponse("Subscription deleted successfully", null));
//    }
//	
//    @PutMapping("/subscriptions/{subId}/assign-delivery/{deliveryBoyId}")
//    public ResponseEntity<ApiResponse> assignDeliveryBoy(
//            @PathVariable Long subId, 
//            @PathVariable Long deliveryBoyId) {
//        
//        subscriptionService.assignDeliveryBoy(subId, deliveryBoyId);
//        
//        return ResponseEntity.ok(new ApiResponse("Delivery Boy assigned to subscription successfully", null));
//    }
//}
