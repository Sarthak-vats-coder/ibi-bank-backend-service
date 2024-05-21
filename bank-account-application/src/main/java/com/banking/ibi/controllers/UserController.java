package com.banking.ibi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.banking.ibi.entities.UserEntity;
import com.banking.ibi.services.UserServices;

@RestControllerAdvice
@RestController
@RequestMapping("/user-service/user")
public class UserController {

	@Autowired
	UserServices userService;
	
	@PostMapping("/createUser")
	public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity){
		return ResponseEntity.ok(userService.createUser(userEntity));
	}
}
