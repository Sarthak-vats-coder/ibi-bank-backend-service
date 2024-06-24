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

import com.banking.ibi.controllers.UserController;
import com.banking.ibi.entities.AccountEntity;
import com.banking.ibi.entities.UserEntity;
import com.banking.ibi.exceptions.UserNotFound;
import com.banking.ibi.repositories.AccountRepository;
import com.banking.ibi.repositories.UserRepository;

@SpringBootTest
class UserControllerTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private UserController userController;

	private UserEntity user1 = UserEntity.builder().username("user1").build();
	private UserEntity user2 = UserEntity.builder().email("user@mail").build();
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
	void deleteAll() {
		userRepository.deleteAll();
	}

	@Test
	void createUser() throws Exception {

		// Arrange
		UserEntity requestBody = UserEntity.builder().build();

		// Act
		ResponseEntity<UserEntity> userEntity = userController.createUser(requestBody);

		// Assert
		assertThat(userEntity.getBody()).usingRecursiveComparison().ignoringFields("userId").isEqualTo(requestBody);
	}

//	@Test
//	void updateUser() throws Exception {
//		// Arrange and Act
//		UserEntity requestBody = UserEntity.builder().build();
//
//		ResponseEntity<ResponseEntity<AuthResponse>> userEntity = userController.createUser(requestBody);
//
//		UserEntity updaterequestBody = UserEntity.builder().userId(userEntity.getBody()).age(5000).build();
//
//		ResponseEntity<UserEntity> userEntity1 = userController.updateUser(updaterequestBody);
//
//		// Assert
//		assertThat(userEntity1.getBody().getAge()).isEqualTo(5000);
//
//	}
	
	@Test
	void updateUserThrowsUserNotFound(){
		// Arrange and Act
		UserEntity requestBody = UserEntity.builder().build();
		UserNotFound exception = assertThrows(UserNotFound.class, () -> userController.updateUser(requestBody));

		// Assert
		assertThat(exception).hasMessage("User Not Found");
		
	}

	@Test
	void deleteUserByUserId() {

		// Act
		userController.deleteUserByUserId(user1.getUserId());

		// Assert
		assertThat(user1).isNotIn(userRepository);

	}

	@Test
	void findUserByUserId() throws UserNotFound {

		// Act
		ResponseEntity<UserEntity> userEntity1 = userController.findUserByUserId(user1.getUserId());

		// Assert
		assertThat(userEntity1.getBody()).usingRecursiveComparison().isEqualTo(user1);

	}

	@Test
	void findUserByUserIdThrowsUserNotFound() {

		// Act
		UserNotFound exception = assertThrows(UserNotFound.class, () -> userController.findUserByUserId(5000));

		// Assert
		assertThat(exception).hasMessage("User Not Found");

	}

	@Test
	void findUserByAccountId() throws UserNotFound {

		// Act
		ResponseEntity<UserEntity> userEntity1 = userController.findUserByAccountId(account1.getAccountId());

		// Assert
		assertThat(userEntity1.getBody()).usingRecursiveComparison().isEqualTo(user1);

	}

	@Test
	void findUserByAccountIdThrowsUserNotFound() {

		// Act
		UserNotFound exception = assertThrows(UserNotFound.class, () -> userController.findUserByAccountId(5000));

		// Assert
		assertThat(exception).hasMessage("User Not Found");

	}

	@Test
	void findUserByUsername() throws UserNotFound {
		// Act
		ResponseEntity<UserEntity> userEntity1 = userController.findUserByUsername(user1.getUsername());

		// Assert
		assertThat(userEntity1.getBody()).usingRecursiveComparison().isEqualTo(user1);

	}

	@Test
	void findUserByUsernameThrowsUserNotFound() {

		// Act
		UserNotFound exception = assertThrows(UserNotFound.class,
				() -> userController.findUserByUsername("test_findUserByUsername"));

		// Assert
		assertThat(exception).hasMessage("User Not Found");

	}

	@Test
	void findUserByEmailAddress() throws UserNotFound {
		// Act
		ResponseEntity<UserEntity> userEntity1 = userController.findUserByEmailAddress(user2.getEmail());

		// Assert
		assertThat(userEntity1.getBody()).usingRecursiveComparison().isEqualTo(user2);

	}

	@Test
	void findUserByEmailAddressThrowsUserNotFound() {

		// Act
		UserNotFound exception = assertThrows(UserNotFound.class,
				() -> userController.findUserByEmailAddress("test_findUserByEmailAddress"));

		// Assert
		assertThat(exception).hasMessage("User Not Found");

	}

	@Test
	void getAllUser() {
		ResponseEntity<List<UserEntity>> userEntity = userController.getAllUser();

		assertThat(userEntity.getBody()).isEqualTo(userRepository.findAll());
	}

}
