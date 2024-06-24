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

import com.banking.ibi.entities.AccountEntity;
import com.banking.ibi.exceptions.AccountNotFound;
import com.banking.ibi.exceptions.DataMismatchException;
import com.banking.ibi.exceptions.UserNotFound;
import com.banking.ibi.models.AccountRequestBody;
import com.banking.ibi.services.AccountServices;

import jakarta.validation.Valid;

@RestController
@RestControllerAdvice
@RequestMapping("/account")
public class AccountController {

	
	AccountServices accountServices;
	public AccountController(AccountServices accountServices) {
		this.accountServices = accountServices;
		
	}
	
	@PostMapping("/create")
	public ResponseEntity<AccountEntity> createAccount(@Valid @RequestBody AccountRequestBody createAccountRequest) throws UserNotFound  {
		return ResponseEntity.ok(accountServices.createAccount(createAccountRequest));
	}
	
	@PostMapping("/updateAccount")
	public ResponseEntity<AccountEntity> updateAccount(@RequestBody AccountEntity accountEntity) throws DataMismatchException, AccountNotFound {
		return ResponseEntity.ok(accountServices.updateAccount( accountEntity));
	}
	
	@GetMapping("/findAccountByAccountId")
	public ResponseEntity<AccountEntity> findAccountByAccountId(@RequestParam long accountId) throws AccountNotFound {
		return ResponseEntity.ok(accountServices.findAccountByAccountId( accountId));
	}
	
	@GetMapping("/findAccountByUsername")
	public ResponseEntity<List<AccountEntity>> findAccountByUsername(@RequestParam String username) throws AccountNotFound {
		return ResponseEntity.ok(accountServices.findAccountByUsername(username));
	}
	
	@GetMapping("/findAccountsByUserId")
	public ResponseEntity<List<AccountEntity>> findAccountsByUserId(@RequestParam long userId) throws AccountNotFound {
		return ResponseEntity.ok(accountServices.findAccountsByUserId(userId));
	}
	
	@DeleteMapping("/deleteAccountById")
	public ResponseEntity<Boolean> deleteAccountById(@RequestParam long accountId) throws AccountNotFound, UserNotFound {
		return ResponseEntity.ok(accountServices.deleteAccountById( accountId));
	}
	
}
