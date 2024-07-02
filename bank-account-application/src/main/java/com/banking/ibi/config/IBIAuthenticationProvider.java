//package com.banking.ibi.config;
//
//import java.util.List;
//
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//public class IBIAuthenticationProvider implements AuthenticationProvider{
//
//	@Override
//	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		return  new UsernamePasswordAuthenticationToken(
//                "Sarthak_Vats", "sarthak", List.of(new SimpleGrantedAuthority("ADMIN")));
//	}
//
//	@Override
//	public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//	}
//
//}
