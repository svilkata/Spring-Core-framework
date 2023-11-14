package org.example.config;

import org.example.service.WebServicePersistenceManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public WebServicePersistenceManager createWebServicePersistenceManager(){
        return new WebServicePersistenceManager();
    }

}
