package org.example.lawyerservice.service;

import com.netflix.discovery.converters.Auto;
import lombok.AllArgsConstructor;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.repository.LawyerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class LawyerServiceImpl implements LawyerService{

    private static final Logger LOGGER
            = LoggerFactory.getLogger(LawyerServiceImpl.class);

    @Autowired
    private final LawyerRepository lawyerRepository;
    @Override
    public Lawyer addLawyer(Lawyer lawyer) {
        LOGGER.info("Adding Lawyer: {}", lawyer.getName());
        return lawyerRepository.save(lawyer);
    }

    @Override
    public Lawyer getLawyerByID(String id) {
        LOGGER.info("Found Lawyer by Id: {}", id);
        return lawyerRepository.findLawyerById(id);
    }

    @Override
    public Lawyer getLawyerByName(String name) {
        LOGGER.info("Found Lawyer by name: {}", name);
        return lawyerRepository.findLawyerByName(name);
    }

    @Override
    public List<Lawyer> getAllLawyers() {
        LOGGER.info("Found All Lawyers");
        return lawyerRepository.findAll();
    }

    @Override
    public Lawyer updateLawyerById(String id, Lawyer lawyer) { // to implement
        LOGGER.info("Lawyer with id: {} was updated", id);
        return null;
    }

    @Override
    public Lawyer updateLawyerByName(String name, Lawyer lawyer) { // to implement
        LOGGER.info("Lawyer with name: {} was updated", name);
        return null;
    }

    @Override
    public Lawyer deleteById(String id) { // to implement
        LOGGER.info("Lawyer with id: {} was deleted", id);
        return null;
    }

    @Override
    public Lawyer deleteLawyerByName(String name) { // to implement
        LOGGER.info("Lawyer with nam: {} was deleted", name);
        return null;
    }

    @Override
    public String deleteAll() {
        lawyerRepository.deleteAll();
        return "All Lawyers were removed from database!";
    }
}
