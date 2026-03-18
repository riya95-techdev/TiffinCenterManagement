package com.example.tiffin_center_management.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

	private LocalDateTime timestamp;
	private String message;
	private int status;
	private String path;
	
}
