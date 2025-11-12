package org.example.lawclientservice.repository;

import org.example.lawclientservice.domain.LawCase;
import org.example.lawclientservice.domain.LawClient;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@Testcontainers
class LawClientRepositoryTest {

    @Container
    static MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:4.4.2"));

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private LawClientRepository lawClientRepository;

    LawClient first;

    @BeforeEach
    void setUp(){
        lawClientRepository.deleteAll();
        first = new LawClient("1","name");
    }

    @Test
    @DisplayName("Connection Test")
    void connectionWorks() { // check of the connection with Mongo DB from the Docker
        assertThat(mongoDBContainer.isCreated()).isTrue();
        assertThat(mongoDBContainer.isRunning()).isTrue();
    }

    @Test
    @DisplayName("FindById Test")
    void getLawyerByIdTest(){
        String generatedID = UUID.randomUUID().toString();
        first.setId(generatedID);
        lawClientRepository.save(first);
        LawClient founded = lawClientRepository.findById(generatedID)
                .orElseThrow(() -> new AssertionError("LawCase not found by id"));
        assertNotNull(founded);
    }

    @Test
    @DisplayName("FindByName Test")
    void getLawyerByNameTest(){
        String name = "First LawCase";
        first.setName(name);
        lawClientRepository.save(first);
        LawClient founded = lawClientRepository.findLawClientByName(name)
                .orElseThrow(() -> new AssertionError("LawCase not found by nmae"));
        assertNotNull(founded);
    }

    @Test
    @DisplayName("DeleteById Test")
    void deleteLawyerById(){
        String generatedID = UUID.randomUUID().toString();
        String name1 = "First LawCase";
        first = new LawClient(generatedID, name1);
        int repository_size = lawClientRepository.findAll().size();
        assertThat(repository_size).isZero();
        lawClientRepository.save(first);
        repository_size = lawClientRepository.findAll().size();
        assertThat(repository_size).isOne();
        LawClient deleted = lawClientRepository.deleteLawClientById(generatedID)
                .orElseThrow(() -> new AssertionError("LawCase not found by id"));
        assertNotNull(deleted);
        repository_size = lawClientRepository.findAll().size();
        assertThat(repository_size).isZero();

    };

    @Test
    @DisplayName("DeleteByName Test")
    void deleteByName(){
        String generatedID = UUID.randomUUID().toString();
        String name1 = "First LawCase";
        first = new LawClient(generatedID, name1);
        int repository_size = lawClientRepository.findAll().size();
        assertThat(repository_size).isZero();
        lawClientRepository.save(first);
        repository_size = lawClientRepository.findAll().size();
        assertThat(repository_size).isOne();
        LawClient deleted = lawClientRepository.deleteLawClientByName(name1)
                .orElseThrow(() -> new AssertionError("LawCase not found by name"));
        assertNotNull(deleted);
        repository_size = lawClientRepository.findAll().size();
        assertThat(repository_size).isZero();
    }

}