package com.example.tiffin_center_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.tiffin_center_management.payload.CustomerDTO;
import com.example.tiffin_center_management.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerContoller {

	@Autowired
	private final CustomerService service;

    // CREATE
    @PostMapping
    public CustomerDTO create(@RequestBody CustomerDTO dto) {
        return service.create(dto);
    }

    // READ ALL (pagination)
    @GetMapping
    public Page<CustomerDTO> getAll(@RequestParam int page,
                                   @RequestParam int size) {
        return service.getAll(page, size);
    }

    // READ ONE
    @GetMapping("/{id}")
    public CustomerDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public CustomerDTO update(@PathVariable Long id,
                              @RequestBody CustomerDTO dto) {
        return service.update(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Customer deleted";
    }
	
}
