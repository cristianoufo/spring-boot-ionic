package com.cristiano.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.cristiano.cursomc.services.DBService;
import com.cristiano.cursomc.services.EmailService;
import com.cristiano.cursomc.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBService service;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instantiateDataBase() throws ParseException {
        if (!"create".equals(strategy)) {
            return false;
        }
        service.instantiateTestDatabase();
        return true;
    }

    @Bean
    public EmailService getEmailService() {
        return new SmtpEmailService();
    }

}
