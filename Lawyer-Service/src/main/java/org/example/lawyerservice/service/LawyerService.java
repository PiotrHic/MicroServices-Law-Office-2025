package org.example.lawyerservice.service;

import org.example.lawyerservice.domain.Lawyer;

import java.util.List;

public interface LawyerService {

    // C
    Lawyer addLawyer(Lawyer lawyer);

    // R
    Lawyer getLawyerByID(String id);
    Lawyer getLawyerByName(String name);
    List<Lawyer> getAllLawyers();

    Lawyer updateLawyerById(String id, Lawyer lawyer);
    Lawyer updateLawyerByName(String name, Lawyer lawyer);

    // D
    Lawyer deleteLawyerById(String id);
    Lawyer deleteLawyerByName(String name);
    String deleteAlLawyers();
}
