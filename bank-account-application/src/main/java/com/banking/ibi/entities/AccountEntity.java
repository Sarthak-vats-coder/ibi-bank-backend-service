package com.banking.ibi.entities;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long accountId;
	private String accountType;
	private double accountBal;
	private Timestamp lastUpdatedAt;
	private Timestamp createdAt;
	private boolean isEnabled;
	private boolean isBlacklisted;
	private boolean isFrozen;
	
}
