package com.banking.ibi.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.banking.ibi.models.ErrorResponse;

@RestControllerAdvice
@RestController
public class ExceptionController {

	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateValueError(Exception ex) {
		return new ResponseEntity<ErrorResponse>(
				new ErrorResponse("Email or username already exists", "Duplicate value encountered while saving"),
				HttpStatus.BAD_REQUEST);

	}
}
