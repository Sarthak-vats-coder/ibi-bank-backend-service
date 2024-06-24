package com.banking.ibi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.banking.ibi.entities.UserEntity;
import com.banking.ibi.exceptions.UserNotFound;
import com.banking.ibi.models.LoginRequest;
import com.banking.ibi.response.AuthResponse;
import com.banking.ibi.services.UserServices;

import jakarta.servlet.http.HttpServletResponse;

@RestControllerAdvice
@RestController
@RequestMapping("/user-service/user")
public class UserController {

	UserServices userService;
	public UserController(	UserServices userService) {
		this.userService = userService;
		
	}
	
	@GetMapping("/GetAllUser")
	public ResponseEntity<List<UserEntity>> getAllUser() {
		return ResponseEntity.ok(userService.getAllUser());
	}

	
	@PostMapping("/createUser")
	public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity) throws Exception{
		return ResponseEntity.ok(userService.createUser(userEntity));
	}
	
	@PostMapping("/signIn")
    public ResponseEntity<ResponseEntity<AuthResponse>> signIn(@RequestBody LoginRequest userEntity, HttpServletResponse response) {
		return  ResponseEntity.ok(userService.signIn(userEntity,response)); }
	

	@PostMapping("/updateUser")
	public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity userEntity) throws UserNotFound{
		return ResponseEntity.ok(userService.updateUser(userEntity));
	}
	
	@DeleteMapping("/deleteUserByUserId")
	public ResponseEntity<Boolean> deleteUserByUserId(@RequestParam long userId){
		return ResponseEntity.ok(userService.deleteUserByUserId(userId));
	}
	
	@GetMapping("/findUserByUserId")
	public ResponseEntity<UserEntity> findUserByUserId(@RequestParam long userId) throws UserNotFound{
		return ResponseEntity.ok(userService.findUserByUserId(userId));
	}
	
	@GetMapping("/findUserByAccountId")
	public ResponseEntity<UserEntity> findUserByAccountId(@RequestParam long accountId) throws UserNotFound{
		return ResponseEntity.ok(userService.findUserByAccountId(accountId));
	}
	@GetMapping("/findUserByUsername")
	public ResponseEntity<UserEntity> findUserByUsername(@RequestParam String username) throws UserNotFound{
		return ResponseEntity.ok(userService.findUserByUsername(username));
	}
	
	@GetMapping("/findUserByEmailAddress")
	public ResponseEntity<UserEntity> findUserByEmailAddress(@RequestParam String email) throws UserNotFound{
		return ResponseEntity.ok(userService.findUserByEmailAddress(email));
	}
	
}

