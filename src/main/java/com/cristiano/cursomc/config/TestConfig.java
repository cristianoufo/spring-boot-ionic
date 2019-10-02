package com.cristiano.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.cristiano.cursomc.services.DBService;
import com.cristiano.cursomc.services.EmailService;
import com.cristiano.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService service;
	@Bean
	public boolean instantiateDataBase() throws ParseException {
		service.instantiateDataBase();
		return true;
	}
        @Bean
      public EmailService getEmailService(){
          return new MockEmailService();
      }

}
