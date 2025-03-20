package vttp.batch4.csf.ecommerce.controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import vttp.batch4.csf.ecommerce.exceptions.ItemNotSavedException;
import vttp.batch4.csf.ecommerce.exceptions.OrderNotSavedException;
import vttp.batch4.csf.ecommerce.models.ErrorMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotSavedException.class) 
    public ResponseEntity<ErrorMessage> handleOrderNotSavedException(OrderNotSavedException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(400, ex.getMessage(), LocalDateTime.now(), request.getRequestURI());
        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(errorMessage);
    }

    @ExceptionHandler(ItemNotSavedException.class) 
    public ResponseEntity<ErrorMessage> handleItemNotSavedException(ItemNotSavedException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(400, ex.getMessage(), LocalDateTime.now(), request.getRequestURI());
        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(errorMessage);
    }
    
}
