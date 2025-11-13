package org.example.lawclientservice.controller;

import io.restassured.RestAssured;
import org.example.lawclientservice.domain.LawClient;
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

import static io.restassured.RestAssured.given;
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
        lawClientRepository.deleteAll();
    }

    @Test
    void testCreateLawClient() {
        String requestBody = """
                {
                  "name": "Piotr Hic"
                }
                """;

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/lawclient")
                .then()
                .statusCode(201)
                .body("name", equalTo("Piotr Hic"));
    }

    @Test
    void testGetLawClientById() {
        LawClient lawClient = lawClientRepository.save(new LawClient(null, "Piotr Hic"));

        given()
                .when()
                .get("/api/lawclient/getById/" + lawClient.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo("Piotr Hic"));
    }

    @Test
    void testGetLawClientByName() {
        lawClientRepository.save(new LawClient(null, "Piotr Hic"));

        given()
                .queryParam("lawClientName", "Piotr Hic")
                .when()
                .get("/api/lawclient/getByName")
                .then()
                .statusCode(200)
                .body("name", equalTo("Piotr Hic"));
    }

    @Test
    void testGetAllLawClients() {
        lawClientRepository.save(new LawClient(null, "Piotr Hic1"));
        lawClientRepository.save(new LawClient(null, "Piotr Hic2"));

        given()
                .when()
                .get("/api/lawclient/getAllLawClients")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2));
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