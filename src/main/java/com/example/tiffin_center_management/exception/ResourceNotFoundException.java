package com.example.tiffin_center_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException(String message) {
        super(message);
    }

	private String resourceName; // Example: "Customer"
    private String fieldName;    // Example: "id"
    private long fieldValue;    // Example: 101

    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        // Message format: "Customer not found with id : 101"
        super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
