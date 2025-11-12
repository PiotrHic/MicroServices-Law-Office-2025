package org.example.lawclientservice.repository;

import org.example.lawclientservice.domain.LawClient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LawClientRepository extends MongoRepository<LawClient, String> {

    Optional<LawClient> findLawClientById(String id);
    Optional<LawClient> findLawClientByName(String name);
    Optional<LawClient> deleteLawClientById(String id);
    Optional<LawClient> deleteLawClientByName(String name);
}
