package com.banking.ibi.service_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking.ibi.config.JwtTokenProvider;
import com.banking.ibi.entities.UserEntity;
import com.banking.ibi.exceptions.UserNotFound;
import com.banking.ibi.models.LoginRequest;
import com.banking.ibi.repositories.AccountRepository;
import com.banking.ibi.repositories.UserRepository;
import com.banking.ibi.response.AuthResponse;
import com.banking.ibi.services.UserServices;
import com.banking.ibi.utils.IBIConstants;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserServices {

	AccountRepository accountRepository;
	UserRepository userRepository;
	PasswordEncoder passwordEncoder;

	public UserServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder,
			UserRepository userRepository) {
		this.accountRepository = accountRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserEntity createUser(@RequestBody UserEntity userEntity) throws Exception {
		String username = userEntity.getUsername();
		Optional<UserEntity> usernameExist = userRepository.findByUsername(username);
		if (usernameExist.isPresent() && usernameExist.get().getUsername() != null) {
			throw new Exception("username Is Already Used With Another Account" + usernameExist);
		}
		UserEntity user = new UserEntity();
		user.setUsername(userEntity.getUsername());
		user.setFname(userEntity.getFname());
		user.setLname(userEntity.getLname());
		user.setEmail(userEntity.getEmail());
		user.setAge(userEntity.getAge());
		user.setPassword(passwordEncoder.encode(userEntity.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest, HttpServletResponse response)
			throws UserNotFound {

		String username = loginRequest.getUsername();
		UserEntity user = findUserByUsername(username);
		String UserPassword = user.getPassword();
		String password = loginRequest.getPassword();

		if (user != null && passwordEncoder.matches(password, UserPassword)) {

			String token = JwtTokenProvider.generateToken(loginRequest);
			
			AuthResponse authResponse = new AuthResponse();

			Cookie jwtCookie = new Cookie("authCookie", token);
			jwtCookie.setDomain("localhost");
			jwtCookie.setPath("/");
			jwtCookie.setHttpOnly(true);
			jwtCookie.setSecure(true);
			jwtCookie.setMaxAge(24 * 3600);

			response.addCookie(jwtCookie);
			authResponse.setMessage("success");
			authResponse.setJwt(token);

			return new ResponseEntity<>(authResponse, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
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
