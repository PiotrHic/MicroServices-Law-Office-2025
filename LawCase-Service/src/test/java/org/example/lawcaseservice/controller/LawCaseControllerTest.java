package org.example.lawcaseservice.controller;

import io.restassured.RestAssured;
import org.example.lawcaseservice.domain.DTO.LawCaseDTO;
import org.example.lawcaseservice.domain.LawCase;
import org.example.lawcaseservice.repository.LawCaseRepository;
import org.example.lawyerservice.domain.Lawyer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class LawCaseControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    ModelMapper modelMapper;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.4");

    @Autowired
    private LawCaseRepository lawCaseRepository;

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        lawCaseRepository.deleteAll();
    }

    @Test
    void testCreateLawCase() {
        String requestBody = """
                {
                  "name": "Civil Law Case"
                }
                """;

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/lawcase")
                .then()
                .statusCode(201)
                .body("name", equalTo("Civil Law Case"));
    }

    @Test
    void testGetLawCaseById() {
        LawCase lawCase = lawCaseRepository.save(new LawCase(null, "Civil Law Case"));

        given()
                .when()
                .get("/api/lawcase/getById/" + lawCase.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo("Civil Law Case"));
    }

    @Test
    void testGetLawCaseByName() {
        lawCaseRepository.save(new LawCase(null, "Piotr Hic"));

        given()
                .queryParam("lawCaseName", "Piotr Hic")
                .when()
                .get("/api/lawcase/getByName")
                .then()
                .statusCode(200)
                .body("name", equalTo("Piotr Hic"));
    }

    @Test
    void testGetAllLawyers() {
        lawCaseRepository.save(new LawCase(null, "Piotr Hic1"));
        lawCaseRepository.save(new LawCase(null, "Piotr Hic2"));

        given()
                .when()
                .get("/api/lawcase/getAllLawCases")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2));

    }

    @Test
    void testUpdateLawCaseById() {

        LawCase lawCase = lawCaseRepository.save(new LawCase(null, "Civil Law Case"));

        String updateRequest = """
            {
              "name": "Criminal Law Case"
            }
            """;

        given()
                .contentType("application/json")
                .body(updateRequest)
                .when()
                .put("/api/lawcase/updateById/" + lawCase.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo("Criminal Law Case"));
    }


    @Test
    void testUpdateLawCaseByName() {

        lawCaseRepository.save(new LawCase(null, "Civil Law Case"));

        String updateRequest = """
            {
              "name": "Criminal Law Case"
            }
            """;

        given()
                .contentType("application/json")
                .queryParam("lawCaseName", "Civil Law Case")
                .body(updateRequest)
                .when()
                .put("/api/lawcase/updateByName")
                .then()
                .statusCode(200)
                .body("name", equalTo("Criminal Law Case"));
    }

    @Test
    void testDeleteLawCaseById() {
        // Arrange — insert a lawyer in test MongoDB
        LawCase lawCase = lawCaseRepository.save(new LawCase(null, "Civil Law Case"));

        // Act + Assert — call DELETE endpoint
        given()
                .when()
                .delete("/api/lawcase/deleteById/" + lawCase.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo("Civil Law Case"));

    }

    @Test
    void testDeleteLawCaseByName() {
        lawCaseRepository.save(new LawCase(null, "Civil Law Case"));

        given()
                .queryParam("lawCaseName", "Civil Law Case")
                .when()
                .delete("/api/lawcase/deleteByName")
                .then()
                .statusCode(200)
                .body("name", equalTo("Civil Law Case"));
    }

    @Test
    void testDeleteAllLawCases() {
        lawCaseRepository.save(new LawCase(null, "Piotr Hic1"));
        lawCaseRepository.save(new LawCase(null, "Piotr Hic2"));

        given()
                .when()
                .delete("/api/lawcase/deleteAll")
                .then()
                .statusCode(200)
                .body(equalTo("Database is empty"));

        // verify DB is empty
        assert(lawCaseRepository.count() == 0);
    }
    @Test
    void testGetLawCaseById_NotFound() {
        // Try to get a non-existing lawyer id
        String nonExistingId = "999999";

        given()
                .when()
                .get("/api/lawcase/getById/" + nonExistingId)
                .then()
                .statusCode(404)
                .body("status", equalTo(404))
                .body("error", equalTo("Not Found"))
                .body("message", equalTo("LawCase with id: " + nonExistingId + " was not found!"));
    }


}
