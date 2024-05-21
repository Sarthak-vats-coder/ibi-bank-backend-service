package com.banking.ibi.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.banking.ibi.entities.AccountEntity;
import com.banking.ibi.services.AccountServices;

@Service
public class AccountServiceImpl implements AccountServices {

	@Override
	public AccountEntity createAccount(AccountEntity accountEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountEntity updateAccountById(long accountId, AccountEntity accountEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountEntity updateAccount(AccountEntity accountEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteAccountById(long accountId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AccountEntity findAccountByAccountId(long accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AccountEntity> findAccountsByUserId(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AccountEntity> findAccountByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
