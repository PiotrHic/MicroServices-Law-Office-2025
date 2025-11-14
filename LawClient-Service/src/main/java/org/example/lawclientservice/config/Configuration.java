package org.example.lawclientservice.config;

import org.example.lawclientservice.client.LawCaseClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public WebClient lawCaseWebClient() {
        return WebClient.builder()
                .baseUrl("http://Lawcase-Service")
                .build();
    }

    @Bean
    public LawCaseClient lawCaseClient() {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory
                        .builderFor(WebClientAdapter.create(lawCaseWebClient()))
                        .build();
        return httpServiceProxyFactory.createClient(LawCaseClient.class);
    }
}
