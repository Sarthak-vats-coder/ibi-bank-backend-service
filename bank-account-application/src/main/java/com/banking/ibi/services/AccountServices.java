package com.banking.ibi.services;

import java.util.List;

import com.banking.ibi.entities.AccountEntity;

public interface AccountServices {

	AccountEntity createAccount(AccountEntity accountEntity);
	AccountEntity updateAccountById(long accountId, AccountEntity accountEntity);
	AccountEntity updateAccount(AccountEntity accountEntity);
	boolean deleteAccountById(long accountId);
	AccountEntity findAccountByAccountId(long accountId);
	List<AccountEntity> findAccountsByUserId(long userId);
	List<AccountEntity> findAccountByUsername(String username);
	
}
