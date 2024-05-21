package com.banking.ibi.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfig {

	@Bean
	DataSource mysqlDataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setUsername("root");
		dataSource.setPassword("sarthak");
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/bankapp");
		return dataSource;
	}
}
