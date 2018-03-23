package com.inventory.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.inventory")
@EnableJpaRepositories("com.inventory.repository")
@EntityScan("com.inventory.domain")
public class InventoryManagementApplication {

	/**
	 * Main Application class to start the boot application for Inventory Management
	 * @param args
	 */
	public static void main(String[] args) {

		SpringApplication.run(InventoryManagementApplication.class, args);

	}

}
