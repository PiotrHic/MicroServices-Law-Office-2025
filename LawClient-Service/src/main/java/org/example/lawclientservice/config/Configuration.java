package org.example.lawclientservice.config;

import org.example.lawclientservice.mapper.LawClientMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public LawClientMapper lawClientMapper() {
        return new LawClientMapper();
    }


}
