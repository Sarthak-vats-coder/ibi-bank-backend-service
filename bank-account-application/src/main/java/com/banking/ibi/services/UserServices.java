package com.banking.ibi.services;

import com.banking.ibi.entities.UserEntity;
import com.banking.ibi.exceptions.DataMismatchException;

public interface UserServices {

	UserEntity createUser(UserEntity userEntity);

	UserEntity updateUserByUserId(long userId, UserEntity userEntity) throws DataMismatchException;

	UserEntity updateUser(UserEntity userEntity);

	boolean deleteUserByUserId(long userId);

	UserEntity findUserByUserId(long userId);

	UserEntity findUserByAccountId(long accountId);

	UserEntity findUserByUsername(String username);

	UserEntity findUserByEmailAddress(String email);

}
