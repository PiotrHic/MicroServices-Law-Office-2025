package org.example.lawcaseservice.service;

import org.example.lawcaseservice.domain.LawCase;

import java.util.Set;

public interface LawCaseService {

    LawCase createLawCase(LawCase LawCase);
    LawCase getLawCase (Integer id);
    Set<LawCase> getAllLawCases();
    LawCase updateLawCaseById (String id, LawCase LawCase);
    LawCase updateLawCaseByName (String id, LawCase LawCase);
    String deleteLawCaseById (String id);
    LawCase deleteLawCaseByName (String name);
    String deleteAllLawCases();
}
