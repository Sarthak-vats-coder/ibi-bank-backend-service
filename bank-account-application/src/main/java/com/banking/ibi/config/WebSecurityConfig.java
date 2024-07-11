package com.banking.ibi.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	private final AuthenticationProvider authenticationProvider;
    private final JwtTokenValidator jwtTokenValidator;

    public WebSecurityConfig(
        JwtTokenValidator jwtTokenValidator,
        AuthenticationProvider authenticationProvider
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtTokenValidator = jwtTokenValidator;
    }
	


	@Bean
	@Primary
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(
					authorize -> authorize
					.requestMatchers("/user-service/user/signIn").permitAll()
					.requestMatchers("/swagger-ui/**").permitAll()
					.requestMatchers("/v3/api-docs/**").permitAll()
					.requestMatchers("/user-service/user/createUser").permitAll()
					.anyRequest().authenticated())
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtTokenValidator, UsernamePasswordAuthenticationFilter.class)
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.build();
	}
	
	private CorsConfigurationSource corsConfigurationSource() { 
        return new CorsConfigurationSource() { 
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) { 
                CorsConfiguration ccfg = new CorsConfiguration(); 
                ccfg.setAllowedOrigins(Arrays.asList(
                		"http://localhost:3000",
                		"http://127.0.0.1:3000",
                		"http://localhost:1025",
                		"http://127.0.0.1:1025")); 
                ccfg.setAllowedMethods(Collections.singletonList("*")); 
                ccfg.setAllowCredentials(true); 
                ccfg.setAllowedHeaders(Collections.singletonList("*")); 
                ccfg.setMaxAge(3600L); 
              
                return ccfg; 
                
  
            } 
        }; 
        
	}
  
}
