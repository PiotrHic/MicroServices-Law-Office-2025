package org.example.lawyerservice.repository;

import org.example.lawyerservice.domain.Lawyer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LawyerRepository extends MongoRepository<Lawyer, String> {

    Object findLawyerById(String id);
    Object findLawyerByName(String name);
}
