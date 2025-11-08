package org.example.lawyerservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.example.lawyerservice.domain.DTO.LawyerDTO;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.repository.LawyerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class LawyerControllerTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.4");

    @DynamicPropertySource
    static void configureMongo(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @LocalServerPort
    private int port;

    @Autowired
    private LawyerRepository lawyerRepository;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        lawyerRepository.deleteAll();
    }

    @Test
    void testCreateLawyer() {
        String requestBody = """
                {
                  "name": "Harvey Specter"
  
                }
                """;

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/lawyer")
                .then()
                .statusCode(201)
                .body("name", equalTo("Harvey Specter"));
    }

    @Test
    void testGetLawyerById() {
        Lawyer lawyer = lawyerRepository.save(new Lawyer(null, "Mike Ross"));

        given()
                .when()
                .get("/api/lawyer/getById/" + lawyer.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo("Mike Ross"));
    }

    @Test
    void testGetLawyerByName() {
        lawyerRepository.save(new Lawyer(null, "Piotr Hic"));

        given()
                .queryParam("lawyerName", "Piotr Hic")
                .when()
                .get("/api/lawyer/getByName")
                .then()
                .statusCode(200)
                .body("name", equalTo("Piotr Hic"));
    }

    @Test
    void testGetAllLawyers() {
        lawyerRepository.save(new Lawyer(null, "Piotr Hic1"));
        lawyerRepository.save(new Lawyer(null, "Piotr Hic2"));

        given()
                .when()
                .get("/api/lawyer/getAllLawyers")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2));

    }

    @Test
    void testUpdateLawyerById() {
        // Arrange: Insert a lawyer
        Lawyer lawyer = lawyerRepository.save(new Lawyer(null, "Old Name"));

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
                .put("/api/lawyer/updateById/" + lawyer.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo("New Name"));
    }


    @Test
    void testUpdateLawyerByName() {
        // Arrange
        lawyerRepository.save(new Lawyer(null, "Piotr Hic"));

        String updateRequest = """
            {
              "name": "New Name"
            }
            """;

        given()
                .contentType("application/json")
                .queryParam("lawyerName", "Piotr Hic")
                .body(updateRequest)
                .when()
                .put("/api/lawyer/updateByName")
                .then()
                .statusCode(200)
                .body("name", equalTo("New Name"));
    }

    @Test
    void testDeleteLawyerById() {
        // Arrange — insert a lawyer in test MongoDB
        Lawyer lawyer = lawyerRepository.save(new Lawyer(null, "PH"));

        // Act + Assert — call DELETE endpoint
        given()
                .when()
                .delete("/api/lawyer/deleteById/" + lawyer.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo("PH"));

    }

    @Test
    void testDeleteLawyerByName() {
        lawyerRepository.save(new Lawyer(null, "Piotr Hic"));

        given()
                .queryParam("lawyerName", "Piotr Hic")
                .when()
                .delete("/api/lawyer/deleteByName")
                .then()
                .statusCode(200)
                .body("name", equalTo("Piotr Hic"));
    }

    @Test
    void testDeleteAllLawyers() {
        lawyerRepository.save(new Lawyer(null, "PH1"));
        lawyerRepository.save(new Lawyer(null, "PH2"));

        given()
                .when()
                .delete("/api/lawyer/deleteAll")
                .then()
                .statusCode(200)
                .body(equalTo("Database is empty"));

        // verify DB is empty
        assert(lawyerRepository.count() == 0);
    }

    @Test
    void testGetLawyerById_NotFound() {
        // Try to get a non-existing lawyer id
        String nonExistingId = "999999";

        given()
                .when()
                .get("/api/lawyer/getById/" + nonExistingId)
                .then()
                .statusCode(404)
                .body("status", equalTo(404))
                .body("error", equalTo("Not Found"))
                .body("message", equalTo("Lawyer with id: " + nonExistingId + " was not found!"));
    }
}