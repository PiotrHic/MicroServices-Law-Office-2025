package org.example.lawcaseservice.repository;

import org.example.lawcaseservice.domain.LawCase;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LawCaseRepository extends MongoRepository<LawCase, String> {

    Optional<LawCase> findLawCaseById(String id);
    Optional<LawCase> findLawCaseByName(String name);
    Optional<LawCase> deleteLawCaseById(String id);
    Optional<LawCase> deleteLawCaseByName(String name);
}
