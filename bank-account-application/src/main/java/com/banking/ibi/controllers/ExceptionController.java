package com.banking.ibi.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.banking.ibi.models.ErrorResponse;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
@RestController
public class ExceptionController {

	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateValueError(Exception ex) {
		return new ResponseEntity<>(
				new ErrorResponse("Email or username already exists", "Duplicate value encountered while saving"),
				HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(value= {NoSuchElementException.class})
	public ResponseEntity<ErrorResponse> handleNoElemetFound(Exception ex) {
		return new ResponseEntity<>(
				new ErrorResponse("No element found", "Please check if it was saved correctly"),
				HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(value = {ConstraintViolationException.class})
	public ResponseEntity<ErrorResponse> handleValidationErrors(ConstraintViolationException exception){
		List<String> messages = new ArrayList<>();
		exception.getConstraintViolations().forEach(violation -> 
			messages.add(violation.getConstraintDescriptor().getMessageTemplate())
		);
		return new ResponseEntity<>(ErrorResponse
				.builder()
				.errorMessage(messages.toString())
				.build(), HttpStatus.BAD_REQUEST);
	}
}
