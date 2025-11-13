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
        // Arrange: Insert a lawyer
        LawClient lawClient = lawClientRepository.save(new LawClient(null, "Old Name"));

        String updateRequest = """
            {
              "name": "New Name"
            }
            """;

        // Act + Assert
        given()
                .contentType("application/json")
                .body(updateRequest)
                .when()
                .put("/api/lawclient/updateById/" + lawClient.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo("New Name"));
    }


    @Test
    void testUpdateLawClientByName() {
        // Arrange
        lawClientRepository.save(new LawClient(null, "Piotr Hic"));

        String updateRequest = """
            {
              "name": "New Name"
            }
            """;

        given()
                .contentType("application/json")
                .queryParam("lawClientName", "Piotr Hic")
                .body(updateRequest)
                .when()
                .put("/api/lawclient/updateByName")
                .then()
                .statusCode(200)
                .body("name", equalTo("New Name"));
    }

    @Test
    void testDeleteLawClientById() {
        // Arrange — insert a lawyer in test MongoDB
        LawClient lawClient = lawClientRepository.save(new LawClient(null, "Piotr Hic"));

        // Act + Assert — call DELETE endpoint
        given()
                .when()
                .delete("/api/lawclient/deleteById/" + lawClient.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo("Piotr Hic"));

    }

    @Test
    void testDeleteLawClientByName() {
        lawClientRepository.save(new LawClient(null, "Piotr Hic"));

        given()
                .queryParam("lawClientName", "Piotr Hic")
                .when()
                .delete("/api/lawclient/deleteByName")
                .then()
                .statusCode(200)
                .body("name", equalTo("Piotr Hic"));
    }

    @Test
    void testDeleteAllLawClients() {
        lawClientRepository.save(new LawClient(null, "Piotr Hic1"));
        lawClientRepository.save(new LawClient(null, "Piotr Hic2"));

        given()
                .when()
                .delete("/api/lawclient/deleteAll")
                .then()
                .statusCode(200)
                .body(equalTo("Database is empty"));

        // verify DB is empty
        assert(lawClientRepository.count() == 0);
    }

    @Test
    void testGetLawClientById_NotFound() {
// Try to get a non-existing lawyer id
        String nonExistingId = "999999";

        given()
                .when()
                .get("/api/lawclient/getById/" + nonExistingId)
                .then()
                .statusCode(404)
                .body("status", equalTo(404))
                .body("error", equalTo("Not Found"))
                .body("message", equalTo("LawClient with id: " + nonExistingId + " was not found!"));
    }

}