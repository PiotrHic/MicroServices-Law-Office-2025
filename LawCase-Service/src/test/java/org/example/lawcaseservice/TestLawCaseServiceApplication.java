package org.example.lawcaseservice;

import org.springframework.boot.SpringApplication;

public class TestLawCaseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(LawCaseServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
