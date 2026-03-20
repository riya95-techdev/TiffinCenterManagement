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

    // 1. Resource Not Found (404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return build(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    // 2. Security Alert & Custom Business Logic Errors (400 or 403)
    // Ye wahi handler hai jo markDelivered() ki security check ko handle karega
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        // Agar message mein "Security" ya "not authorized" hai toh 403 Forbidden bhejenge
        if (ex.getMessage().toLowerCase().contains("security") || ex.getMessage().toLowerCase().contains("authorized")) {
            status = HttpStatus.FORBIDDEN;
        }
        
        return build(ex.getMessage(), status, request);
    }

    // 3. Email Already Exists
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailExists(EmailAlreadyExistsException ex, HttpServletRequest request) {
        return build(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    // 4. Invalid Credentials (401)
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCred(InvalidCredentialsException ex, HttpServletRequest request) {
        return build(ex.getMessage(), HttpStatus.UNAUTHORIZED, request);
    }

    // 5. Validation Errors (DTO @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // 6. Access Denied (Spring Security)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        return build("Access Denied: You don't have permission!", HttpStatus.FORBIDDEN, request);
    }

    // 7. DB Duplicate Error
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDBError(DataIntegrityViolationException ex, HttpServletRequest request) {
        return build("Database Error: Duplicate value or constraint violation", HttpStatus.CONFLICT, request);
    }

    // 8. Final Catch-All (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex, HttpServletRequest request) {
        return build("Something went wrong: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    // ✅ Common Builder for Clean JSON
    private ResponseEntity<ErrorResponse> build(String msg, HttpStatus status, HttpServletRequest request) {
        ErrorResponse err = new ErrorResponse(
                LocalDateTime.now(),
                msg,
                status.value(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(err, status);
    }
}




//package com.example.tiffin_center_management.exception;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import com.example.tiffin_center_management.payload.ApiResponse;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//@RestControllerAdvice
//public class GlobalException {
//
////	 @ExceptionHandler(ResourceNotFoundException.class)
////	    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
////	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
////	                .body(Map.of(
////	                        "timestamp", LocalDateTime.now(),
////	                        "message", ex.getMessage(),
////	                        "status", 404
////	                ));
////	    }
//	
//	@ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFoundException ex) {
//        // Jab ID nahi milegi, toh ye 404 error bhejega, "Success" nahi!
//        return new ResponseEntity<>(new ApiResponse(ex.getMessage(), null), HttpStatus.NOT_FOUND);
//    }
//
//	    @ExceptionHandler(IllegalArgumentException.class)
//	    public ResponseEntity<?> handleBadRequest(IllegalArgumentException ex) {
//	        return ResponseEntity.badRequest()
//	                .body(Map.of(
//	                        "timestamp", LocalDateTime.now(),
//	                        "message", ex.getMessage(),
//	                        "status", 400
//	                ));
//	    }
//
//	    @ExceptionHandler(MethodArgumentNotValidException.class)
//	    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
//	        Map<String, String> errors = new HashMap<>();
//	        ex.getBindingResult().getFieldErrors()
//	                .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
//
//	        return ResponseEntity.badRequest().body(errors);
//	    }
//
//	    @ExceptionHandler(AccessDeniedException.class)
//	    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
//	        return ResponseEntity.status(HttpStatus.FORBIDDEN)
//	                .body(Map.of("message", "Access Denied"));
//	    }
//
//	    @ExceptionHandler(Exception.class)
////	    public ResponseEntity<?> handleAll(Exception ex) {
////	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////	                .body(Map.of(
////	                        "timestamp", LocalDateTime.now(),
////	                        "message", ex.getMessage(),
////	                        "status", 500
////	                ));
////	    }
//	    public ResponseEntity<ErrorResponse> handleAll(
//	            Exception ex,
//	            HttpServletRequest request) {
//
//	        return build(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
//	    }
//	    
//	 // ✅ Common builder
//	    private ResponseEntity<ErrorResponse> build(
//	            String msg,
//	            HttpStatus status,
//	            HttpServletRequest request) {
//
//	        ErrorResponse err = new ErrorResponse(
//	                LocalDateTime.now(),
//	                msg,
//	                status.value(),
//	                request.getRequestURI()
//	        );
//
//	        return new ResponseEntity<>(err, status);
//	    }
//	    
//	 // 🔴 Email already exists
//	    @ExceptionHandler(EmailAlreadyExistsException.class)
//	    public ResponseEntity<?> handleEmailExists(EmailAlreadyExistsException ex) {
//	        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
//	    }
//	    
//	    //invalid password or login
//	    @ExceptionHandler(InvalidCredentialsException.class)
//	    public ResponseEntity<?> handleInvalidCred(InvalidCredentialsException ex) {
//	        return buildResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
//	    }
//	    
//	 // 🔴 DB duplicate error (backup safety)
//	    @ExceptionHandler(DataIntegrityViolationException.class)
//	    public ResponseEntity<?> handleDBError(DataIntegrityViolationException ex) {
//	        return buildResponse("Duplicate value found", HttpStatus.BAD_REQUEST);
//	    }
//	    
//	 //Common Response Builder
//	    private ResponseEntity<Map<String, Object>> buildResponse(String msg, HttpStatus status) {
//	        Map<String, Object> map = new HashMap<>();
//	        map.put("timestamp", LocalDateTime.now());
//	        map.put("message", msg);
//	        map.put("status", status.value());
//
//	        return new ResponseEntity<>(map, status);
//	    }
//	
//}
