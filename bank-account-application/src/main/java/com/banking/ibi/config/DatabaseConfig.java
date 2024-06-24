package com.banking.ibi.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfig {

	@Value("${spring.sarthak.mysql.url}")
	private String jdbcUrl;
	@Value("${spring.sarthak.mysql.username}")
	private String username;
	@Value("${spring.sarthak.mysql.password}")
	private String password;
	
	@Bean
	DataSource mysqlDataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setJdbcUrl(jdbcUrl);
		return dataSource;
	}
	
	
}
