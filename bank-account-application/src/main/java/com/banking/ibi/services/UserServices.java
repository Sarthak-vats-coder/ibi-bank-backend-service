package com.banking.ibi.services;

import java.util.List;

import com.banking.ibi.entities.UserEntity;
import com.banking.ibi.exceptions.UserNotFound;

public interface UserServices {

	UserEntity createUser(UserEntity userEntity);

	UserEntity updateUser(UserEntity userEntity) throws UserNotFound;

	boolean deleteUserByUserId(long userId);

	UserEntity findUserByUserId(long userId) throws UserNotFound;

	UserEntity findUserByAccountId(long accountId) throws UserNotFound;

	UserEntity findUserByUsername(String username) throws UserNotFound;

	UserEntity findUserByEmailAddress(String email) throws UserNotFound;

	List<UserEntity> getAllUser();
}
