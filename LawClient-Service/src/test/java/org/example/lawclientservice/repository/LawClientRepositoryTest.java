package org.example.lawclientservice.repository;

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
    private LawyerRepository lawyerRepository;

    @BeforeEach
    void setUp(){
        lawyerRepository.deleteAll();
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
        Lawyer first = new Lawyer(generatedID, "First Lawyer");
        lawyerRepository.save(first);
        Lawyer founded = lawyerRepository.findById(generatedID)
                .orElseThrow(() -> new AssertionError("Lawyer not found"));
        assertNotNull(founded);
    }

    @Test
    @DisplayName("FindByName Test")
    void getLawyerByNameTest(){
        String generatedID = UUID.randomUUID().toString();
        String name = "First Lawyer";
        Lawyer first = new Lawyer(generatedID, name);
        lawyerRepository.save(first);
        Lawyer founded = lawyerRepository.findLawyerByName(name)
                .orElseThrow(() -> new AssertionError("Lawyer not found"));
        assertNotNull(founded);
    }

    @Test
    @DisplayName("DeleteById Test")
    void deleteLawyerById(){
        String generatedID = UUID.randomUUID().toString();
        String name1 = "First Lawyer";
        Lawyer first = new Lawyer(generatedID, name1);
        lawyerRepository.save(first);
        int repository_size = lawyerRepository.findAll().size();
        assertThat(repository_size).isOne();
        Lawyer deleted = lawyerRepository.deleteLawyerById(generatedID)
                .orElseThrow(() -> new AssertionError("Lawyer not found"));
        assertNotNull(deleted);
        repository_size = lawyerRepository.findAll().size();
        assertThat(repository_size).isZero();

    };


    @Test
    @DisplayName("DeleteByName Test")
    void deleteByName(){
        String generatedID = UUID.randomUUID().toString();
        String name1 = "First Lawyer";
        Lawyer first = new Lawyer(generatedID, name1);
        int repository_size = lawyerRepository.findAll().size();
        assertThat(repository_size).isZero();
        lawyerRepository.save(first);
        repository_size = lawyerRepository.findAll().size();
        assertThat(repository_size).isOne();
        Lawyer deleted = lawyerRepository.deleteLawyerByName(name1)
                .orElseThrow(() -> new AssertionError("Lawyer not found"));
        assertNotNull(deleted);
        repository_size = lawyerRepository.findAll().size();
        assertThat(repository_size).isZero();
    }
}