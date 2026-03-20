package com.example.tiffin_center_management.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiffin_center_management.payload.SubscriptionDTO;
import com.example.tiffin_center_management.service.SubscriptionService;

import lombok.RequiredArgsConstructor;

//@PreAuthorize("hasRole('CUSTOMER')")
@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

	private final SubscriptionService service;

    // CREATE(naya order plan lena)
	@PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/subscribe")
    public SubscriptionDTO create(@RequestBody SubscriptionDTO dto) {
        return service.create(dto);
    }

 // Delivery Boy ke liye: Sirf apna kaam dekhna aur mark karna
	@PreAuthorize("hasRole('DELIVERY')")
    @GetMapping("/my-deliveries/{dbId}")
    public Page<SubscriptionDTO> getByDelivery(@PathVariable Long dbId, 
    		@RequestParam(defaultValue = "0") int page,
    	    @RequestParam(defaultValue = "5") int size) { 
        return service.getByDeliveryBoy(dbId, page, size); 
    }
    
 // Mark delivered
	@PreAuthorize("hasRole('DELIVERY')")
    @PutMapping("/deliver/{id}/mark-delivered")
    public String markDelivered(@PathVariable Long id) {
        service.markDelivered(id);
        return "Marked delivered";
    }
    
//    // GET ALL
//    @GetMapping
//    public Page<SubscriptionDTO> getAll(
//    		@RequestParam(defaultValue = "0") int page,
//    	    @RequestParam(defaultValue = "5") int size,
//    	    @RequestParam(defaultValue = "id") String sortBy,
//    	    @RequestParam(defaultValue = "asc") String sortDir
//    		) {
//        return service.getAll(page, size,sortBy,sortDir);
//    }

//    // GET BY ID
//    @GetMapping("/{id}")
//    public SubscriptionDTO getById(@PathVariable Long id) {
//        return service.getById(id);
//    }

//    // UPDATE
//    @PutMapping("/{id}")
//    public SubscriptionDTO update(@PathVariable Long id,
//                                  @RequestBody SubscriptionDTO dto) {
//        return service.update(id, dto);
//    }

//    // DELETE
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable Long id) {
//        service.delete(id);
//        return "Deleted";
//    }

//    // Customer wise
//    @GetMapping("/customer/{customerId}")
//    public Page<SubscriptionDTO> getByCustomer(@PathVariable Long customerId,
//                                               int page, int size) {
//        return service.getByCustomer(customerId, page, size);
//    }

//    // Delivery boy wise
//    @GetMapping("/delivery/{deliveryBoyId}")
//    public Page<SubscriptionDTO> getByDelivery(@PathVariable Long deliveryBoyId,
//    		@RequestParam(defaultValue = "0") int page,
//    		@RequestParam(defaultValue = "5")int size) {
//        return service.getByDeliveryBoy(deliveryBoyId, page, size);
//    }
	
}
