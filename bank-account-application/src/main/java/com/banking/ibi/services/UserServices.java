package com.banking.ibi.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.banking.ibi.entities.UserEntity;
import com.banking.ibi.exceptions.UserNotFound;
import com.banking.ibi.models.LoginRequest;
import com.banking.ibi.response.AuthResponse;

import jakarta.servlet.http.HttpServletResponse;

public interface UserServices {

	UserEntity createUser(UserEntity userEntity) throws Exception;

	UserEntity updateUser(UserEntity userEntity) throws UserNotFound;

	boolean deleteUserByUserId(long userId);

	UserEntity findUserByUserId(long userId) throws UserNotFound;

	UserEntity findUserByAccountId(long accountId) throws UserNotFound;

	UserEntity findUserByUsername(String username) throws UserNotFound;

	UserEntity findUserByEmailAddress(String email) throws UserNotFound;

	List<UserEntity> getAllUser();
	ResponseEntity<AuthResponse> signIn(LoginRequest userEntity, HttpServletResponse response) throws UserNotFound;
}
