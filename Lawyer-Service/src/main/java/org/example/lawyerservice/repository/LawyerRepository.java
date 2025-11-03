package org.example.lawyerservice.repository;

import org.example.lawyerservice.domain.Lawyer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LawyerRepository extends MongoRepository<Lawyer, String> {

    Optional<Lawyer> findLawyerById(String id);
    Optional<Lawyer> findLawyerByName(String name);
    Optional<Lawyer> deleteLawyerById(String id);
    Optional<Lawyer> deleteLawyerByName(String name);
}
