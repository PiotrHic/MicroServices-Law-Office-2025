package org.example.lawyerservice.repository;

import org.example.lawyerservice.domain.Lawyer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LawyerRepository extends MongoRepository<Lawyer, String> {

    Lawyer findLawyerById(String id);
    Lawyer findLawyerByName(String name);
}
