package org.example.lawyerservice.config;

import org.example.lawyerservice.mapper.LawyerMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public LawyerMapper lawyerMapper() {
        return new LawyerMapper();
    }
}
