package org.example.lawyerservice.service;


import lombok.AllArgsConstructor;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.exception.LawyerNotFoundException;
import org.example.lawyerservice.mapper.LawyerMapper;
import org.example.lawyerservice.repository.LawyerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.mongodb.internal.operation.OperationHelper.LOGGER;

@AllArgsConstructor
@Service
public class LawyerServiceImpl implements LawyerService{

    @Autowired
    private final LawyerRepository lawyerRepository;

    @Override
    public Lawyer addLawyer(Lawyer lawyer) {
        return lawyerRepository.save(lawyer);
    }

    @Override
    public Lawyer getLawyerByID(String id) {
        return lawyerRepository
                .findLawyerById(id)
                .orElseThrow(() -> new LawyerNotFoundException("Lawyer with id: " + id + " was not found!"));
    }

    @Override
    public Lawyer getLawyerByName(String name) {
        return lawyerRepository
                .findLawyerByName(name)
                .orElseThrow(() -> new LawyerNotFoundException("Lawyer with name: " + name + " was not found!"));
    }

    @Override
    public List<Lawyer> getAllLawyers() {
        return lawyerRepository.findAll();
    }

    @Override
    public Lawyer updateLawyerById(String id, Lawyer lawyer) {
        Lawyer toUpdate = getLawyerByID(id);
        toUpdate.setId(id);
        toUpdate.setName(lawyer.getName());
        toUpdate.setLawCaseList(lawyer.getLawCaseList());
        lawyerRepository.deleteLawyerById(id);
        lawyerRepository.save(toUpdate);
        return toUpdate;
    }

    @Override
    public Lawyer updateLawyerByName(String name, Lawyer lawyer) { // to implement
        Lawyer toUpdate = getLawyerByName(name);
        toUpdate.setId(lawyer.getId());
        toUpdate.setName(lawyer.getName());
        toUpdate.setLawCaseList(lawyer.getLawCaseList());
        lawyerRepository.deleteLawyerByName(name);
        lawyerRepository.save(toUpdate);
        return toUpdate;
    }

    @Override
    public Lawyer deleteLawyerById(String id) { // to implement
        return lawyerRepository
                .deleteLawyerById(id)
                .orElseThrow(() -> new LawyerNotFoundException("Lawyer with id: " + id + " was not found!"));
    }

    @Override
    public Lawyer deleteLawyerByName(String name) { // to implement
        return lawyerRepository
                .deleteLawyerByName(name)
                .orElseThrow(() -> new LawyerNotFoundException("Lawyer with name: " + name + " was not found!"));
    }

    @Override
    public String deleteAlLawyers() {
        lawyerRepository.deleteAll();
        return "All Lawyers were removed from database!";
    }
}
