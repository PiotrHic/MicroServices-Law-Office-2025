package org.example.lawcaseservice.service;

import lombok.AllArgsConstructor;
import org.example.lawcaseservice.domain.LawCase;
import org.example.lawcaseservice.repository.LawCaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class LawCaseServiceImpl implements LawCaseService {

    @Autowired
    private final LawCaseRepository lawCaseRepository;


    @Override
    public LawCase createLawCase(LawCase lawCase) {
        return lawCaseRepository.save(lawCase);
    }

    @Override
    public LawCase getLawCaseById(String id) {
        return lawCaseRepository
                .findLawCaseById(id)
                .orElseThrow(() -> new LawCaseNotFoundException("LawCase with id: " + id + " was not founded!"));
    }

    @Override
    public List<LawCase> getAllLawCases() {
        return lawCaseRepository.findAll();
    }

    @Override
    public LawCase updateLawCaseById(String id, LawCase lawCase) {
        LawCase toUpdate = getLawCaseById(id);
        toUpdate.setName(lawCase.getName());
        toUpdate.setLawClient(lawCase.getLawClient());
        toUpdate.setLawClientId(lawCase.getLawClientId());
        toUpdate.setLawyer(lawCase.getLawyer());
        toUpdate.setLawyerId(lawCase.getLawyerId());
        return toUpdate;
    }

    @Override
    public LawCase updateLawCaseByName(String id, LawCase lawCase) {
        LawCase toUpdate = getLawCaseById(id);
        toUpdate.setLawClient(lawCase.getLawClient());
        toUpdate.setLawClientId(lawCase.getLawClientId());
        toUpdate.setLawyer(lawCase.getLawyer());
        toUpdate.setLawyerId(lawCase.getLawyerId());
        return toUpdate;
    }

    @Override
    public LawCase deleteLawCaseById(String id) {
        return lawCaseRepository
                .deleteLawCaseById(id)
                .orElseThrow(() -> new LawyerNotFoundException("LawCase with id: " + id + " was not founded!"));
    }

    @Override
    public LawCase deleteLawCaseByName(String name) {
        return lawCaseRepository
                .deleteLawCaseByName(name)
                .orElseThrow(() -> new LawyerNotFoundException("LawCase with name: " + name + " was not founded!"));
    }

    @Override
    public String deleteAllLawCases() {
        lawCaseRepository.deleteAll();
        return "All LawCases were removed from database!";
    }
}
