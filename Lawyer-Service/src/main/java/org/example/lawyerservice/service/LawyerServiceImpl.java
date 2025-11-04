package org.example.lawyerservice.service;


import lombok.AllArgsConstructor;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.exception.LawyerNotFoundException;
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
        Lawyer added = lawyerRepository.save(lawyer);
        LOGGER.info("Adding Lawyer: {}", lawyer.getName());
        return added;
    }

    @Override
    public Lawyer getLawyerByID(String id) {
        LOGGER.info("Found Lawyer by Id: {}", id);
        return lawyerRepository
                .findLawyerById(id)
                .orElseThrow(() -> new LawyerNotFoundException("Lawyer with id: " + id + " was not founded!"));
    }

    @Override
    public Lawyer getLawyerByName(String name) {
        LOGGER.info("Found Lawyer by name: {}", name);
        return lawyerRepository
                .findLawyerByName(name)
                .orElseThrow(() -> new LawyerNotFoundException("Lawyer with name: " + name + " was not founded!"));
    }

    @Override
    public List<Lawyer> getAllLawyers() {
        LOGGER.info("Found All Lawyers");
        return lawyerRepository.findAll();
    }

    @Override
    public Lawyer updateLawyerById(String id, Lawyer lawyer) {
        Lawyer toUpdate = getLawyerByID(id);
        toUpdate.setName(lawyer.getName());
        toUpdate.setLawCaseList(lawyer.getLawCaseList());
        LOGGER.info("Lawyer with id: {} was updated", id);
        return toUpdate;
    }

    @Override
    public Lawyer updateLawyerByName(String name, Lawyer lawyer) { // to implement
        Lawyer toUpdate = getLawyerByName(name);
        toUpdate.setName(lawyer.getName());
        toUpdate.setLawCaseList(lawyer.getLawCaseList());
        LOGGER.info("Lawyer with name: {} was updated", name);
        return toUpdate;
    }

    @Override
    public Lawyer deleteLawyerById(String id) { // to implement
        LOGGER.info("Lawyer with id: {} was deleted", id);
        return lawyerRepository
                .deleteLawyerById(id)
                .orElseThrow(() -> new LawyerNotFoundException("Lawyer with id: " + id + " was not founded!"));
    }

    @Override
    public Lawyer deleteLawyerByName(String name) { // to implement
        LOGGER.info("Lawyer with nam: {} was deleted", name);
        return lawyerRepository
                .deleteLawyerByName(name)
                .orElseThrow(() -> new LawyerNotFoundException("Lawyer with name: " + name + " was not founded!"));
    }

    @Override
    public String deleteAlLawyers() {
        lawyerRepository.deleteAll();
        return "All Lawyers were removed from database!";
    }
}
