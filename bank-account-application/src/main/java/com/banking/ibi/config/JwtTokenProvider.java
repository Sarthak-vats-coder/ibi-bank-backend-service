package com.banking.ibi.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.banking.ibi.models.LoginRequest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtTokenProvider {

	static SecretKey key = Keys.hmacShaKeyFor(JwTokenConstants.SECRET_KEY.getBytes());

	public static String generateToken(LoginRequest loginRequest) {
		return Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 1000 * 3600 * 24))
				.claim("username", loginRequest.getUsername())
				.claim("password", loginRequest.getPassword())
				.signWith(key)
				.compact();
	}
	public static String getUsernameFromJwTOken(String jwt) {
		return Jwts
			.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(jwt)
			.getBody()
			.get("username")
			.toString();
		
	}
	
	public static String getPasswordFromJwTOken(String jwt) {
		return Jwts
			.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(jwt)
			.getBody()
			.get("password")
			.toString();
		
	}

}
