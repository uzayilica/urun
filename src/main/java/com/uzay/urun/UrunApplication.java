package com.uzay.urun;

import com.uzay.urun.audit.AuditAwareConfigs;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "AuditAwareConfigs")
@SpringBootApplication
public class UrunApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrunApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Welcome to Urun Application");
        };
    }



}
