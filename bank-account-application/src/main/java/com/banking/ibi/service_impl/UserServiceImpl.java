package com.banking.ibi.service_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banking.ibi.entities.UserEntity;
import com.banking.ibi.exceptions.UserNotFound;
import com.banking.ibi.repositories.AccountRepository;
import com.banking.ibi.repositories.UserRepository;
import com.banking.ibi.services.UserServices;
import com.banking.ibi.utils.IBIConstants;


@Service
public class UserServiceImpl implements UserServices {

	AccountRepository accountRepository;

	UserRepository userRepository;

	public UserServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
		this.accountRepository = accountRepository;
		this.userRepository = userRepository;
	}

	@Override
	public UserEntity createUser(UserEntity userEntity) {
		return userRepository.save(userEntity);
	}

	@Override
	public UserEntity updateUser(UserEntity userEntity) throws UserNotFound {
		Optional<UserEntity> optionalUser = userRepository.findById(userEntity.getUserId());
		if (optionalUser.isPresent() && optionalUser.get().getUserId() != 0) {
			return userRepository.save(userEntity);
		} else {
			throw new UserNotFound(IBIConstants.USER_NOT_FOUND);
		}
	}

	@Override
	public boolean deleteUserByUserId(long userId) {
		userRepository.deleteById(userId);
		return true;
	}

	@Override
	public UserEntity findUserByUserId(long userId) throws UserNotFound {
		UserEntity user = null;
		Optional<UserEntity> optionalUser = userRepository.findById(userId);
		if (optionalUser.isEmpty()) {
			throw new UserNotFound(IBIConstants.USER_NOT_FOUND);
		} else {
			user = optionalUser.get();
		}
		return user;

	}

	@Override
	public UserEntity findUserByAccountId(long accountId) throws UserNotFound {
		UserEntity user = null;
		Optional<UserEntity> optionalUser = userRepository.findByAccountId(accountId);
		if (optionalUser.isEmpty()) {
			throw new UserNotFound(IBIConstants.USER_NOT_FOUND);
		} else {
			user = optionalUser.get();
		}
		return user;
	}

	@Override
	public UserEntity findUserByUsername(String username) throws UserNotFound {
		UserEntity user = null;
		Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
		if (optionalUser.isEmpty()) {
			throw new UserNotFound(IBIConstants.USER_NOT_FOUND);
		} else {
			user = optionalUser.get();
		}
		return user;

	}

	@Override
	public UserEntity findUserByEmailAddress(String email) throws UserNotFound {
		UserEntity user = null;
		Optional<UserEntity> optionalUser = userRepository.findUserByEmailAddress(email);
		if (optionalUser.isEmpty()) {
			throw new UserNotFound(IBIConstants.USER_NOT_FOUND);
		} else {
			user = optionalUser.get();
		}
		return user;
	}

	@Override
	public List<UserEntity> getAllUser() {
		return userRepository.findAll();
	}

}
