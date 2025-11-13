package org.example.lawcaseservice.config;

import org.example.lawcaseservice.client.LawClientClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientLawClientConfig {

    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;

    @Bean
    public WebClient lawClientWebClient() {
        return WebClient.builder()
                .baseUrl("http://LawClient-Service")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public LawClientClient lawClientClient() {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory
                        .builderFor(WebClientAdapter.create(lawClientWebClient()))
                        .build();
        return httpServiceProxyFactory.createClient(LawClientClient.class);
    }
}
