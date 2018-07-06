package com.inventory.tracking.InventoryTracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.inventory.tracking.InventoryTracking")

public class InventoryTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryTrackingApplication.class, args);
	}
}
