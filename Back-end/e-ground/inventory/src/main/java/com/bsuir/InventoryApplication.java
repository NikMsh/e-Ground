package com.bsuir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The service that allows work with inventory: create, update, read, delete order.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 * @since 18-12-2018
 */
@SpringBootApplication
@EnableJpaRepositories
public class InventoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }
}
