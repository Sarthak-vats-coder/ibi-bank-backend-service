package com.banking.ibi.service_impl;

import org.springframework.stereotype.Service;

import com.banking.ibi.repositories.UserRepository;
import com.banking.ibi.services.AuthenticatorServices;
@Service
public class AuthenticatorServiceImpl implements AuthenticatorServices {
	
	UserRepository userRepository;
	public AuthenticatorServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	

	@Override
	public Long loginRequest(String username) {
		long userId = userRepository.findByUsername(username).get().getUserId();
		return userId;
	}

}
