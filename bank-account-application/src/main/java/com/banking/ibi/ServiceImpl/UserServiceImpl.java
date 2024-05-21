package com.banking.ibi.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.ibi.entities.UserEntity;
import com.banking.ibi.exceptions.DataMismatchException;
import com.banking.ibi.repositories.AccountRepository;
import com.banking.ibi.repositories.UserRepository;
import com.banking.ibi.services.UserServices;

@Service
public class UserServiceImpl implements UserServices{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Override
	public UserEntity createUser(UserEntity userEntity) {
		return userRepository.save(userEntity);
	}

	@Override
	public UserEntity updateUserByUserId(long userId, UserEntity userEntity) throws DataMismatchException {
		if(userEntity.getUserId()!=0 && userEntity.getUserId()!=userId) throw new DataMismatchException("User id cannot be updated");
		return userRepository.save(userEntity);
	}

	@Override
	public UserEntity updateUser(UserEntity userEntity) {
		return userRepository.save(userEntity);
	}

	@Override
	public boolean deleteUserByUserId(long userId) {
		userRepository.deleteById(userId);
		return true;
	}

	@Override
	public UserEntity findUserByUserId(long userId) {
		return userRepository.findById(userId).get();
	}

	@Override
	public UserEntity findUserByAccountId(long accountId) {
		return null;
	}

	@Override
	public UserEntity findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserEntity findUserByEmailAddress(String email) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

