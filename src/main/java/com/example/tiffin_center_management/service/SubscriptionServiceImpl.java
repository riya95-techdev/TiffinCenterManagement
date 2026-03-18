package com.example.tiffin_center_management.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.tiffin_center_management.exception.ResourceNotFoundException;
import com.example.tiffin_center_management.model.Customer;
import com.example.tiffin_center_management.model.DeliveryBoy;
import com.example.tiffin_center_management.model.Subscription;
import com.example.tiffin_center_management.payload.SubscriptionDTO;
import com.example.tiffin_center_management.repository.CustomerRepository;
import com.example.tiffin_center_management.repository.DeliveryBoyRepository;
import com.example.tiffin_center_management.repository.SubscriptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService{

	 private final SubscriptionRepository repo;
	    private final CustomerRepository customerRepo;
	    private final DeliveryBoyRepository deliveryRepo;
	    private final ModelMapper mapper;

	    // ✅ CREATE
	    @Override
	    public SubscriptionDTO create(SubscriptionDTO dto) {

	        Subscription sub = new Subscription();

	        Customer customer = customerRepo.findById(dto.getCustomerId())
	                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

	        sub.setCustomer(customer);

	        // optional delivery boy
	        if (dto.getDeliveryBoyId() != null) {
	            DeliveryBoy db = deliveryRepo.findById(dto.getDeliveryBoyId())
	                    .orElseThrow(() -> new ResourceNotFoundException("DeliveryBoy not found"));
	            sub.setDeliveryBoy(db);
	        }

	        sub.setPlan(dto.getPlan());
	        sub.setPrice(dto.getPrice());
	        sub.setStartDate(dto.getStartDate());
	        sub.setEndDate(dto.getEndDate());
	        sub.setStatus("ACTIVE");

	        return mapper.map(repo.save(sub), SubscriptionDTO.class);
	    }

	    // ✅ GET ALL
	    @Override
	    public Page<SubscriptionDTO> getAll(int page, int size, String sortBy, String sortDir) {

	        Pageable pageable = PageRequest.of(page, size);

	        return repo.findAll(pageable)
	                .map(sub -> mapper.map(sub, SubscriptionDTO.class));
	    }

	    // ✅ GET BY ID
	    @Override
	    public SubscriptionDTO getById(Long id) {

	        Subscription sub = repo.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

	        return mapper.map(sub, SubscriptionDTO.class);
	    }

	    // ✅ UPDATE
	    @Override
	    public SubscriptionDTO update(Long id, SubscriptionDTO dto) {

	        Subscription sub = repo.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

	        // update customer
	        Customer customer = customerRepo.findById(dto.getCustomerId())
	                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
	        sub.setCustomer(customer);

	        // update delivery boy (optional)
	        if (dto.getDeliveryBoyId() != null) {
	            DeliveryBoy db = deliveryRepo.findById(dto.getDeliveryBoyId())
	                    .orElseThrow(() -> new ResourceNotFoundException("DeliveryBoy not found"));
	            sub.setDeliveryBoy(db);
	        }

	        sub.setPlan(dto.getPlan());
	        sub.setPrice(dto.getPrice());
	        sub.setStartDate(dto.getStartDate());
	        sub.setEndDate(dto.getEndDate());
	        sub.setStatus(dto.getStatus());

	        return mapper.map(repo.save(sub), SubscriptionDTO.class);
	    }

	    // ✅ DELETE
	    @Override
	    public void delete(Long id) {

	        Subscription sub = repo.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

	        repo.delete(sub);
	    }

	    // ✅ GET BY CUSTOMER
	    @Override
	    public Page<SubscriptionDTO> getByCustomer(Long customerId, int page, int size) {

	        Pageable pageable = PageRequest.of(page, size);

	        return repo.findByCustomerId(customerId, pageable)
	                .map(sub -> mapper.map(sub, SubscriptionDTO.class));
	    }

	    // ✅ GET BY DELIVERY BOY
	    @Override
	    public Page<SubscriptionDTO> getByDeliveryBoy(Long deliveryBoyId, int page, int size) {

	        Pageable pageable = PageRequest.of(page, size);

	        return repo.findByDeliveryBoyId(deliveryBoyId, pageable)
	                .map(sub -> mapper.map(sub, SubscriptionDTO.class));
	    }

	    // ✅ MARK DELIVERED
	    @Override
	    public void markDelivered(Long id) {

	        Subscription sub = repo.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

	        sub.setStatus("COMPLETED");

	        repo.save(sub);
	    }
	
}
