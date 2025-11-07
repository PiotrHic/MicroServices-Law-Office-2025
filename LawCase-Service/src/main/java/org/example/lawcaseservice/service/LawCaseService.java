package org.example.lawcaseservice.service;

import org.example.lawcaseservice.domain.LawCase;

import java.util.List;
import java.util.Set;

public interface LawCaseService {

    LawCase createLawCase(LawCase lawCase);
    LawCase getLawCaseById (String id);
    LawCase getLawCaseByName (String id);
    List<LawCase> getAllLawCases();
    LawCase updateLawCaseById (String id, LawCase lawCase);
    LawCase updateLawCaseByName (String id, LawCase lawCase);
    LawCase deleteLawCaseById (String id);
    LawCase deleteLawCaseByName (String name);
    String deleteAllLawCases();
}
