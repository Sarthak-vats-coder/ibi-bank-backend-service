package com.banking.ibi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.banking.ibi.entities.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

	@Query(value = "SELECT * " + "FROM   account_entity " + "WHERE  account_id IN " + "(SELECT accounts_account_id "
			+ "FROM   user_entity_accounts " + "WHERE  user_entity_user_id IN (:userId));", nativeQuery = true)
	Optional<List<AccountEntity>> findAccountByUserId(long userId);

	@Query(value = "SELECT * " + "   FROM   account_entity " + "   WHERE  account_id IN "
			+ "   (SELECT accounts_account_id " + "   FROM   user_entity_accounts "
			+ "   WHERE  user_entity_user_id IN " + "   (SELECT user_id " + "   FROM   user_entity "
			+ "   WHERE username IN ( :username )));" + " ", nativeQuery = true)
	Optional<List<AccountEntity>> findAccountByUserName(String username);

}
