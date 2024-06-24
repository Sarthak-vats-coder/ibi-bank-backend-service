package com.banking.ibi.services;

import org.springframework.stereotype.Service;

@Service
public interface AuthenticatorServices {
	Long loginRequest(String usernmae);
}

