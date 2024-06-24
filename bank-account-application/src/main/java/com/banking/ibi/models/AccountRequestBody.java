package com.banking.ibi.models;

import com.banking.ibi.entities.AccountEntity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountRequestBody {
	AccountEntity accountEntity;
	long userId;
}
