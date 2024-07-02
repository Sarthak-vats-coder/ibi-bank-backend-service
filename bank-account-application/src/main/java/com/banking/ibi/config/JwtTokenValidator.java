package com.banking.ibi.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.banking.ibi.entities.UserEntity;
import com.banking.ibi.repositories.UserRepository;
import com.banking.ibi.services.UserServices;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtTokenValidator extends OncePerRequestFilter {
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	

	
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserServices userServices;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = request.getCookies()[0].getValue();
	
		if (jwt == null) {
			filterChain.doFilter(request, response);
			return;
		}
		String username = jwtTokenProvider.getUsernameFromJwTOken(jwt);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if(username!= null && authentication == null ) {
        	
        	Optional<UserEntity> user = userRepository.findByUsername(username);
        	
        	UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.get(),
    				null, user.get().getAuthorities());
        	
        	authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContextHolderStrategy().getContext().setAuthentication(authToken);
        	
        }

//			SecretKey key = Keys.hmacShaKeyFor(JwTokenConstants.SECRET_KEY.getBytes());
//			Claims claim = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(jwt).getBody();
//			System.out.println(claim);
//
//			String username = String.valueOf(claim.get("username"));
//			String password = String.valueOf(claim.get("password"));
//			System.out.println(username + "-------" + password);

		System.out.println("requeted");
		
//		SecurityContextHolder.getContextHolderStrategy().getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
//			return;

//		
	}

}
