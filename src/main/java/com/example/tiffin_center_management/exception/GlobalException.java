package com.example.tiffin_center_management.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalException {

	 @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(Map.of(
	                        "timestamp", LocalDateTime.now(),
	                        "message", ex.getMessage(),
	                        "status", 404
	                ));
	    }

	    @ExceptionHandler(IllegalArgumentException.class)
	    public ResponseEntity<?> handleBadRequest(IllegalArgumentException ex) {
	        return ResponseEntity.badRequest()
	                .body(Map.of(
	                        "timestamp", LocalDateTime.now(),
	                        "message", ex.getMessage(),
	                        "status", 400
	                ));
	    }

	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	        ex.getBindingResult().getFieldErrors()
	                .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));

	        return ResponseEntity.badRequest().body(errors);
	    }

	    @ExceptionHandler(AccessDeniedException.class)
	    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                .body(Map.of("message", "Access Denied"));
	    }

	    @ExceptionHandler(Exception.class)
//	    public ResponseEntity<?> handleAll(Exception ex) {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	                .body(Map.of(
//	                        "timestamp", LocalDateTime.now(),
//	                        "message", ex.getMessage(),
//	                        "status", 500
//	                ));
//	    }
	    public ResponseEntity<ErrorResponse> handleAll(
	            Exception ex,
	            HttpServletRequest request) {

	        return build("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR, request);
	    }
	    
	 // ✅ Common builder
	    private ResponseEntity<ErrorResponse> build(
	            String msg,
	            HttpStatus status,
	            HttpServletRequest request) {

	        ErrorResponse err = new ErrorResponse(
	                LocalDateTime.now(),
	                msg,
	                status.value(),
	                request.getRequestURI()
	        );

	        return new ResponseEntity<>(err, status);
	    }
	    
	 // 🔴 Email already exists
	    @ExceptionHandler(EmailAlreadyExistsException.class)
	    public ResponseEntity<?> handleEmailExists(EmailAlreadyExistsException ex) {
	        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	    
	    //invalid password or login
	    @ExceptionHandler(InvalidCredentialsException.class)
	    public ResponseEntity<?> handleInvalidCred(InvalidCredentialsException ex) {
	        return buildResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	    }
	    
	 // 🔴 DB duplicate error (backup safety)
	    @ExceptionHandler(DataIntegrityViolationException.class)
	    public ResponseEntity<?> handleDBError(DataIntegrityViolationException ex) {
	        return buildResponse("Duplicate value found", HttpStatus.BAD_REQUEST);
	    }
	    
	 //Common Response Builder
	    private ResponseEntity<Map<String, Object>> buildResponse(String msg, HttpStatus status) {
	        Map<String, Object> map = new HashMap<>();
	        map.put("timestamp", LocalDateTime.now());
	        map.put("message", msg);
	        map.put("status", status.value());

	        return new ResponseEntity<>(map, status);
	    }
	
}
