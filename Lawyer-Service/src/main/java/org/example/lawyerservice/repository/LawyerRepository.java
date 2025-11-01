package org.example.lawyerservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LawyerRepository extends MongoRepository<Object, String> {

    Object findObjectById(String id);
    Object findObjectByName(String name);
}
