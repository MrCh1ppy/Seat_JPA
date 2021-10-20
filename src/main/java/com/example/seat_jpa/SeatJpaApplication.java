package com.example.seat_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author 橙鼠鼠
 */
@SpringBootApplication
@EnableOpenApi
@EnableJpaRepositories
@EnableTransactionManagement
public class SeatJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeatJpaApplication.class, args);
    }

}
