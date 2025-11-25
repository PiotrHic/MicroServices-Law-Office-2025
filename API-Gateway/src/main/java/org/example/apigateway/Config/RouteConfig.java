package org.example.apigateway.Config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("lawyer-service", r -> r
                        .path("/api/lawyer/**")
                        .uri("lb://LAWYER-SERVICE"))
                .route("lawcase-service", r -> r
                        .path("/api/lawcase/**")
                        .uri("lb://LAWCASE-SERVICE"))
                .route("lawclient-service", r -> r
                        .path("/api/lawclient/**")
                        .uri("lb://LAWCLIENT-SERVICE"))
                .build();
    }

}
