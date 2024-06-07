package com.banking.ibi.service_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banking.ibi.entities.AccountEntity;
import com.banking.ibi.entities.UserEntity;
import com.banking.ibi.exceptions.AccountNotFound;
import com.banking.ibi.exceptions.DataMismatchException;
import com.banking.ibi.exceptions.UserNotFound;
import com.banking.ibi.models.AccountRequestBody;
import com.banking.ibi.repositories.AccountRepository;
import com.banking.ibi.repositories.UserRepository;
import com.banking.ibi.services.AccountServices;
import com.banking.ibi.utils.IBIConstants;

import jakarta.transaction.Transactional;

@Service
@Transactional(rollbackOn = { Exception.class })
public class AccountServiceImpl implements AccountServices {

	AccountRepository accountRepository;

	UserRepository userRepository;

	public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
		this.accountRepository = accountRepository;
		this.userRepository = userRepository;
	}

	@Override
	public AccountEntity createAccount(AccountRequestBody createAccountRequest) throws UserNotFound {
		AccountEntity account = accountRepository.save(createAccountRequest.getAccountEntity());
		UserEntity user = null;
		Optional<UserEntity> optionalUser = userRepository.findById(createAccountRequest.getUserId());
		if (optionalUser.isEmpty()) {
			throw new UserNotFound("User Not Found");
		} else {
			user = optionalUser.get();
		}
		user.getAccounts().add(account);
		userRepository.save(user);
		return account;
	}

	@Override
	public AccountEntity updateAccount(AccountEntity accountEntity) throws DataMismatchException, AccountNotFound {
		Optional<AccountEntity> optionalAccount = accountRepository.findById(accountEntity.getAccountId());
		if (optionalAccount.isPresent() && optionalAccount.get().getAccountId() != 0) {
			return accountRepository.save(accountEntity);
		} else {
			throw new DataMismatchException(IBIConstants.ACCOUNT_NOT_FOUND);
		}
	}

	@Override
	public AccountEntity findAccountByAccountId(long accountId) throws AccountNotFound {
		AccountEntity account = null;
		Optional<AccountEntity> optionalAccount = accountRepository.findById(accountId);
		if (optionalAccount.isEmpty()) {
			throw new AccountNotFound(IBIConstants.ACCOUNT_NOT_FOUND);
		} else {
			account = optionalAccount.get();
		}
		return account;
	}

	@Override
	public List<AccountEntity> findAccountByUsername(String username) throws AccountNotFound {
		List<AccountEntity> account = null;
		Optional<List<AccountEntity>> optionalAccount = accountRepository.findAccountByUserName(username);
		if (optionalAccount.isEmpty() || optionalAccount.get().isEmpty()) {
			throw new AccountNotFound(IBIConstants.ACCOUNT_NOT_FOUND);
		} else {
			account = optionalAccount.get();
		}
		return account;

	}

	@Override
	public List<AccountEntity> findAccountsByUserId(long userId) throws AccountNotFound {
		List<AccountEntity> accounts = null;
		Optional<List<AccountEntity>> optionalAccount = accountRepository.findAccountByUserId(userId);
		if (optionalAccount.isEmpty() || optionalAccount.get().isEmpty()) {
			throw new AccountNotFound(IBIConstants.ACCOUNT_NOT_FOUND);
		} else {
			accounts = optionalAccount.get();
		}
		return accounts;

	}

	@Override
	public boolean deleteAccountById(long accountId) throws UserNotFound, AccountNotFound {

		AccountEntity account = null;
		Optional<AccountEntity> optionalAccount = accountRepository.findById(accountId);
		if (optionalAccount.isEmpty()) {
			throw new AccountNotFound("Account not found");
		} else {
			account = optionalAccount.get();
		}
		UserEntity user = null;
		Optional<UserEntity> optionalUser = userRepository.findByAccountId(accountId);
		if (optionalUser.isEmpty()) {
			throw new UserNotFound("User Not Found");
		} else {
			user = optionalUser.get();
		}
		user.getAccounts().remove(account);
		userRepository.save(user);
		accountRepository.deleteById(accountId);
		return true;
	}

}
