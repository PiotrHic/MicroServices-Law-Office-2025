package org.example.lawcaseservice.config;

import org.example.lawcaseservice.mapper.LawCaseMapper;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public LawCaseMapper modelMapper() {
        return new LawCaseMapper();
    }
}
