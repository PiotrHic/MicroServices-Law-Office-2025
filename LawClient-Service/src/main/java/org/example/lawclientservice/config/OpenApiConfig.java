package org.example.lawclientservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@org.springframework.context.annotation.Configuration
public class OpenApiConfig {

    @OpenAPIDefinition(
            info = @Info(
                    title = "LawClient API",
                    contact = @Contact(
                            name = "Piotr Hic",
                            url = "https://github.com/PiotrHic",
                            email = "piotrhic@gmail.com"
                    ),
                    version = "1.0",
                    description = "API documentation for LawClient microservice",
                    summary = "Based by Actuator and LawClient REST Controller, API Description of the functionality " +
                            "of the application"
            ),
            servers =  {
                    @Server(
                            description = "Address of the localhost to run the microservice",
                            url ="http://localhost:8013"
                    )
            }
    )
    public static class OpenApiConfigClass {
    }
}
