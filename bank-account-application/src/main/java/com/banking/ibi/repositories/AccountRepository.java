package com.banking.ibi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.ibi.entities.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long>{

}
