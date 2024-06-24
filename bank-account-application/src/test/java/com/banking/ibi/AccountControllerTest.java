package com.banking.ibi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.banking.ibi.controllers.AccountController;
import com.banking.ibi.entities.AccountEntity;
import com.banking.ibi.entities.UserEntity;
import com.banking.ibi.exceptions.AccountNotFound;
import com.banking.ibi.exceptions.DataMismatchException;
import com.banking.ibi.exceptions.UserNotFound;
import com.banking.ibi.models.AccountRequestBody;
import com.banking.ibi.repositories.AccountRepository;
import com.banking.ibi.repositories.UserRepository;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class AccountControllerTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private AccountController accountController;

	private UserEntity user1 = UserEntity.builder().username("user1").build();
	private UserEntity user2 = UserEntity.builder().build();
	private UserEntity user3 = UserEntity.builder().username("user3").build();

	private AccountEntity account1 = AccountEntity.builder().accountType("SAVINGS").build();
	private AccountEntity account2 = AccountEntity.builder().accountType("CURRENT").build();
	private AccountEntity account3 = AccountEntity.builder().accountType("CREDIT").build();
	private AccountEntity account4 = AccountEntity.builder().accountType("SAVINGS").build();
	private AccountEntity account5 = AccountEntity.builder().accountType("SAVINGS").build();

	@BeforeEach
	void createTestUsersAndAccounts() {
		user1 = userRepository.save(user1);
		user2 = userRepository.save(user2);
		user3 = userRepository.save(user3);
		account1 = accountRepository.save(account1);
		account2 = accountRepository.save(account2);
		account3 = accountRepository.save(account3);
		account4 = accountRepository.save(account4);
		account5 = accountRepository.save(account5);

		user1.setAccounts(Set.of(account1, account2));
		user2.setAccounts(Set.of(account3, account4));

		user1 = userRepository.save(user1);
		user2 = userRepository.save(user2);

	}

	@AfterEach
	void deleteTestUsersAndAccounts() {
		userRepository.deleteAll();
		accountRepository.deleteAll();
	}

	@Test
	void testCreateAccount_shouldReturnSuccess() throws UserNotFound {
		// Arrange
		AccountRequestBody requestBody = AccountRequestBody.builder().userId(user1.getUserId())
				.accountEntity(AccountEntity.builder().accountType("CURRENT").build()).build();

		// Act
		ResponseEntity<AccountEntity> accountEntity = accountController.createAccount(requestBody);

		// Assert
		assertThat(accountEntity.getBody()).usingRecursiveComparison().ignoringFields("accountId")
				.isEqualTo(requestBody.getAccountEntity());

	}

	@Test
	void testCreateAccountWithBlankBody_shouldThrowErrorWith400() {
		// Arrange
		AccountRequestBody requestBody = AccountRequestBody.builder().userId(user1.getUserId())
				.accountEntity(AccountEntity.builder().build()).build();

		// Act
		ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
				() -> accountController.createAccount(requestBody));

		// Assert
		assertThat(exception).hasMessageContaining("Account type null is not allowed");

	}

	@Test
	void testCreateAccountWithUserIdNotFound() {
		// Arrange
		AccountRequestBody requestBody = AccountRequestBody.builder().userId(1000)
				.accountEntity(AccountEntity.builder().accountType("CURRENT").build()).build();

		// Act
		UserNotFound exception = assertThrows(UserNotFound.class, () -> accountController.createAccount(requestBody));

		// Assert
		assertThat(exception).hasMessageContaining("User Not Found");

	}

	@Test
	void testCreateAccountWithUserWhichDontHaveAnyAccount() throws UserNotFound {
		// Arrange
		AccountRequestBody requestBody = AccountRequestBody.builder().userId(user3.getUserId())
				.accountEntity(AccountEntity.builder().accountType("CURRENT").build()).build();

		// Act
		ResponseEntity<AccountEntity> accountEntity = accountController.createAccount(requestBody);

		// Assert
		assertThat(accountEntity.getBody()).usingRecursiveComparison().ignoringFields("accountId")
				.isEqualTo(requestBody.getAccountEntity());
	}

	@Test
	void testUpdateUser() throws UserNotFound, DataMismatchException, AccountNotFound {
		AccountRequestBody requestBody = AccountRequestBody.builder().userId(user3.getUserId())
				.accountEntity(AccountEntity.builder().accountType("CURRENT").build()).build();

		// Act
		ResponseEntity<AccountEntity> accountEntity = accountController.createAccount(requestBody);
		AccountEntity updateUser = AccountEntity.builder().accountId(accountEntity.getBody().getAccountId())
				.accountType("CREDIT").build();

		ResponseEntity<AccountEntity> updatedAccountEntity = accountController.updateAccount(updateUser);

		assertThat(updatedAccountEntity.getBody().getAccountType()).isEqualTo("CREDIT");
	}

	@Test
	void testUpdateUserThrowsDataMismatchException() {


		// Act
		AccountEntity updateUser = AccountEntity.builder().accountId(0).accountType("CREDIT").build();

		DataMismatchException exception = assertThrows(DataMismatchException.class,
				() -> accountController.updateAccount(updateUser));

		// Assert
		assertThat(exception).hasMessageContaining("Account Not found");
	}

	@Test
	void testCreateAndFetchUser() {
		UserEntity user = UserEntity.builder().build();
		user = userRepository.save(user);
		assertThat(userRepository.findById(user.getUserId()).get().getAccounts()).isEmpty();
	}

	@Test
	void testFindAccountByAccountId() throws AccountNotFound, UserNotFound {
		// Arrange
		AccountRequestBody requestBody = AccountRequestBody.builder().userId(user1.getUserId())
				.accountEntity(AccountEntity.builder().accountType("SAVINGS").build()).build();
		// Act
		ResponseEntity<AccountEntity> accountEntity = accountController.createAccount(requestBody);
		long accountId = accountEntity.getBody().getAccountId();
		ResponseEntity<AccountEntity> accountResponse = accountController.findAccountByAccountId(accountId);

		// Assert
		assertThat(accountResponse.getBody()).usingRecursiveComparison().isEqualTo(accountEntity.getBody());
	}

	@Test
	void testFindAccountByAccountIdThrowsAccountNotFound() {
		// Arrange
		// Act
		AccountNotFound exception = assertThrows(AccountNotFound.class,
				() -> accountController.findAccountByAccountId(10000));

		// Assert
		assertThat(exception).hasMessageContaining("Account Not found");
	}

	@Test
	void testFindAccountByUsername() throws AccountNotFound {
		// Arrange
		String username = user1.getUsername();
		// Act
		ResponseEntity<List<AccountEntity>> accountsReponse = accountController.findAccountByUsername(username);
		// Assert
		assertThat(accountsReponse.getBody()).usingRecursiveComparison().isEqualTo(user1.getAccounts());
	}

	@Test
	void testFindAccountByUsernameThrowsAccountNotFound() {
		// Arrange

		// Act
		AccountNotFound exception = assertThrows(AccountNotFound.class,
				() -> accountController.findAccountByUsername(user3.getUsername()));

		// Assert
		assertThat(exception).hasMessageContaining("Account Not found");
	}

	@Test
	void testFindAccountsByUserId() throws AccountNotFound {
		// Arrange
		// Act
		long userId = user1.getUserId();
		ResponseEntity<List<AccountEntity>> accountsReponse = accountController.findAccountsByUserId(userId);

		// Assert
		assertThat(accountsReponse.getBody()).usingRecursiveComparison().isEqualTo(user1.getAccounts());
	}

	@Test
	void testFindAccountsByUserIdThrowsAccountNotFound() {
		// Arrange

		// Act
		AccountNotFound exception = assertThrows(AccountNotFound.class,
				() -> accountController.findAccountsByUserId(5000));

		// Assert
		assertThat(exception).hasMessageContaining("Account Not found");
	}

	@Test
	void testDeleteAccountById() throws UserNotFound, AccountNotFound {
		// Arrange
		AccountRequestBody requestBody = AccountRequestBody.builder().userId(user1.getUserId())
				.accountEntity(AccountEntity.builder().accountType("SAVINGS").build()).build();
		ResponseEntity<AccountEntity> accountEntity = accountController.createAccount(requestBody);
		long accountId = accountEntity.getBody().getAccountId();
		accountController.deleteAccountById(accountId);

		assertThat(user1.getAccounts()).doesNotContain((accountEntity.getBody()));

	}

	@Test
	void testDeleteAccountByIdThrowsAccountNotFound() {
		// Arrange
		AccountNotFound exception = assertThrows(AccountNotFound.class,
				() -> accountController.deleteAccountById(5000));

		// Assert
		assertThat(exception).hasMessageContaining("Account not found");

	}

	@Test
	void testDeleteAccountByIdThrowsUserNotFound() {
		// Arrange
		UserNotFound exception = assertThrows(UserNotFound.class,
				() -> accountController.deleteAccountById(account5.getAccountId()));

		// Assert
		assertThat(exception).hasMessageContaining("User Not Found");

	}

}
