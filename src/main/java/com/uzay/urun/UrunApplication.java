package com.uzay.urun;

import com.uzay.urun.audit.AuditAwareConfigs;
import com.uzay.urun.config.ConfigProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.Map;
@EnableKafka
@EnableWebSecurity
@EnableConfigurationProperties(ConfigProperty.class)
@EnableJpaAuditing(auditorAwareRef = "AuditAwareConfigs")
@SpringBootApplication


public class UrunApplication {

    @Autowired ConfigProperty configProperty;

    public static void main(String[] args) {
        SpringApplication.run(UrunApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println(Map.of(
                    "isim",configProperty.getIsim(),
                    "password",configProperty.getPassword(),
                    "yetenek",configProperty.getYetenek().get(0),
                    "vucut",configProperty.getVucut().get("boy")));

        };
    }



}
