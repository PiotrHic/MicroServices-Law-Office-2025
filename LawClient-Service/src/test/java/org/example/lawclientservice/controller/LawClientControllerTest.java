package org.example.lawclientservice.controller;

import org.example.lawclientservice.repository.LawClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class LawClientControllerTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.4");

    @DynamicPropertySource
    static void configureMongo(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @LocalServerPort
    private int port;

    @Autowired
    private LawClientRepository lawClientRepository;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        lawyerRepository.deleteAll();
    }

    @Test
    void testCreateLawClient() {
    }

    @Test
    void testGetLawClientById() {

    }

    @Test
    void testGetLawClientByName() {
    }

    @Test
    void testGetAllLawClients() {
    }

    @Test
    void testUpdateLawClientById() {

    }


    @Test
    void testUpdateLawClientByName() {

    }

    @Test
    void testDeleteLawClientById() {

    }

    @Test
    void testDeleteLawClientByName() {

    }

    @Test
    void testDeleteAllLawClients() {

    }

    @Test
    void testGetLawClientById_NotFound() {

    }

}