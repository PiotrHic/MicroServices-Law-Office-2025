package org.example.lawcaseservice.config;


import org.example.lawcaseservice.webclient.LawyerWebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientLawyerConfig {


    @Bean
    public WebClient lawyerWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8011")
                .build();
    }

    @Bean
    public LawyerWebClient lawyerClient() {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory
                        .builderFor(WebClientAdapter.create(lawyerWebClient()))
                        .build();
        return httpServiceProxyFactory.createClient(LawyerWebClient.class);
    }

}
