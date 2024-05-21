package com.banking.ibi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.ibi.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
