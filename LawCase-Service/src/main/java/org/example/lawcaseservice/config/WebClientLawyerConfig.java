package org.example.lawcaseservice.config;


import org.example.lawcaseservice.client.LawyerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientLawyerConfig {

    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;

    @Bean
    public WebClient lawyerWebClient() {
        return WebClient.builder()
                .baseUrl("http://Lawyer-Service")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public LawyerClient lawyerClient() {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory
                        .builderFor(WebClientAdapter.create(lawyerWebClient()))
                        .build();
        return httpServiceProxyFactory.createClient(LawyerClient.class);
    }

}
