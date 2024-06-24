package com.banking.ibi.config;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator  extends OncePerRequestFilter{

	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	private static int dummy = 0;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(dummy++%3==0) {
			filterChain.doFilter(request, response);
			return;
		}
		System.out.println("requeted");
		UsernamePasswordAuthenticationToken authentication =  new UsernamePasswordAuthenticationToken(
                "anubhav", "anubhav", List.of(new SimpleGrantedAuthority("ADMIN")));
        SecurityContextHolder.getContextHolderStrategy().getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}
	

}
