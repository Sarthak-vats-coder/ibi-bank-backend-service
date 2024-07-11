package com.banking.ibi.config;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.banking.ibi.entities.UserEntity;
import com.banking.ibi.repositories.UserRepository;
import com.banking.ibi.services.UserServices;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenValidator extends OncePerRequestFilter {

	JwtTokenProvider jwtTokenProvider;
	UserServices userServices;
	UserRepository userRepository;

	public JwtTokenValidator(JwtTokenProvider jwtTokenProvider, UserServices userServices,
			UserRepository userRepository) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.userServices = userServices;
		this.userRepository = userRepository;

	}
	
	private Cookie getAuthCookie(HttpServletRequest request) {
		if(request==null || request.getCookies().length==0 || request.getCookies()[0].getValue()== null) return null;
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("auth_token")) return cookie;
		}
		return null;
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		
		Cookie authCookie = getAuthCookie(request);
		
		if(authCookie==null) {
			filterChain.doFilter(request, response);
			return;
		}
		
		final String jwt = authCookie.getValue();
		String username = JwtTokenProvider.getUsernameFromJwTOken(jwt);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (username != null && authentication == null) {
			Optional<UserEntity> user = userRepository.findByUsername(username);
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					user.get().getUsername(), 
					user.get().getPassword(), 
					List.of(new SimpleGrantedAuthority("ADMIN")));

			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authToken);

		}
		

		filterChain.doFilter(request, response);

	}

}
