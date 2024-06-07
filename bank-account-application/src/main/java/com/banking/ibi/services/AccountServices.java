package com.banking.ibi.services;

import java.util.List;

import com.banking.ibi.entities.AccountEntity;
import com.banking.ibi.exceptions.AccountNotFound;
import com.banking.ibi.exceptions.DataMismatchException;
import com.banking.ibi.exceptions.UserNotFound;
import com.banking.ibi.models.AccountRequestBody;

public interface AccountServices {

	AccountEntity createAccount(AccountRequestBody createAccountRequest) throws  UserNotFound;
	AccountEntity updateAccount(AccountEntity accountEntity) throws DataMismatchException, AccountNotFound;
	boolean deleteAccountById( long accountId) throws  AccountNotFound, UserNotFound;
	AccountEntity findAccountByAccountId(long accountId) throws AccountNotFound ;
	List<AccountEntity> findAccountsByUserId(long userId) throws AccountNotFound ;
	List<AccountEntity> findAccountByUsername(String username) throws AccountNotFound  ;
	
}
