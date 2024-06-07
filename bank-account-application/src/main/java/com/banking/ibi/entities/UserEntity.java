package com.banking.ibi.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	@Column(unique = true)
	@Pattern(regexp = "[\\w]{3,20}")
	private String username;
	private String password;
	private String fname;
	private String lname;
	@Column(unique = true)
	private String email;
	private int age; 
	@OneToMany(fetch = FetchType.EAGER)
	private Set<AccountEntity> accounts;
	
}
