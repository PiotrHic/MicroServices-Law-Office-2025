package org.example.lawclientservice;

import org.springframework.boot.SpringApplication;

public class TestLawClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(LawClientServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
