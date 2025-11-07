package org.example.lawcaseservice.repository;

import org.assertj.core.api.Assertions;
import org.example.lawcaseservice.domain.LawCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@Testcontainers
public class LawCaseRepositoryTest {

    @Container
    static MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:4.4.2"));

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private LawCaseRepository lawCaseRepository;


    @BeforeEach
    void setUp(){
        lawCaseRepository.deleteAll();
    }

    @Test
    @DisplayName("Connection Test")
    void connectionWorks() { // check of the connection with Mongo DB from the Docker
        Assertions.assertThat(mongoDBContainer.isCreated()).isTrue();
        Assertions.assertThat(mongoDBContainer.isRunning()).isTrue();
    }

    @Test
    @DisplayName("FindById Test")
    void shouldGetLawyerByIdTest(){
        String generatedID = UUID.randomUUID().toString();
        LawCase first = new LawCase(generatedID, "First LawCase");
        lawCaseRepository.save(first);
        LawCase founded = lawCaseRepository.findById(generatedID)
                .orElseThrow(() -> new AssertionError("LawCase not found"));
        assertNotNull(founded);
    }

    @Test
    @DisplayName("FindByName Test")
    void shouldGetLawyerByNameTest(){
        String generatedID = UUID.randomUUID().toString();
        String name = "First LawCase";
        LawCase first = new LawCase(generatedID, name);
        lawCaseRepository.save(first);
        LawCase founded = lawCaseRepository.findLawCaseByName(name)
                .orElseThrow(() -> new AssertionError("LawCase not found"));
        assertNotNull(founded);
    }

    @Test
    @DisplayName("DeleteById Test")
    void shouldDeleteLawyerById(){
        String generatedID = UUID.randomUUID().toString();
        String name1 = "First LawCase";
        LawCase first = new LawCase(generatedID, name1);
        lawCaseRepository.save(first);
        int repository_size = lawCaseRepository.findAll().size();
        Assertions.assertThat(repository_size).isOne();
        LawCase deleted = lawCaseRepository.deleteLawCaseById(generatedID)
                .orElseThrow(() -> new AssertionError("LawCasa not found"));
        assertNotNull(deleted);
        repository_size = lawCaseRepository.findAll().size();
        Assertions.assertThat(repository_size).isZero();
    };


    @Test
    @DisplayName("DeleteByName Test")
    void shouldDeleteByName(){
        String generatedID = UUID.randomUUID().toString();
        String name1 = "First LawCase";
        LawCase first = new LawCase(generatedID, name1);
        int repository_size = lawCaseRepository.findAll().size();
        Assertions.assertThat(repository_size).isZero();
        lawCaseRepository.save(first);
        repository_size = lawCaseRepository.findAll().size();
        Assertions.assertThat(repository_size).isOne();
        LawCase deleted = lawCaseRepository.deleteLawCaseByName(name1)
                .orElseThrow(() -> new AssertionError("LawCase not found"));
        assertNotNull(deleted);
        repository_size = lawCaseRepository.findAll().size();
        Assertions.assertThat(repository_size).isZero();
    };
}
