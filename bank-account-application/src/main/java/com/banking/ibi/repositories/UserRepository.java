package com.banking.ibi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.banking.ibi.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Query(value="select * "
			+ "from user_entity "
			+ "where user_id in "
			    + "(select user_entity_user_id "
			       + "from user_entity_accounts "
			       + "where accounts_account_id in (:accountId))", nativeQuery=true)
	Optional<UserEntity> findByAccountId(long accountId);
	
	
	@Query(value="select * from user_entity where username in (:username)", nativeQuery=true)
	Optional<UserEntity> findByUsername(String username);
	
	@Query(value="select * from user_entity where email in (:email)", nativeQuery=true)
	Optional<UserEntity> findUserByEmailAddress(String email);

}
