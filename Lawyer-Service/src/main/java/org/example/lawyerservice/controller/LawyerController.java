package org.example.lawyerservice.controller;

import lombok.AllArgsConstructor;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.service.LawyerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawyer")
public class LawyerController {

    private final LawyerService lawyerService;

    private final String NUMBER_PATH = "{lawyerId}";
    private final String PATH_VARIABLE_PATH = "lawyerId";

    Lawyer addLawyer(Lawyer lawyer) {
        return null;
    }
    Lawyer getLawyerByID(String id) {
        return null;
    }
    Lawyer getLawyerByName(String name) {
        return null;
    }
    List<Lawyer> getAllLawyers() {
        return null;
    }
    Lawyer updateLawyerById(String id, Lawyer lawyer) {
        return null;
    }
    Lawyer updateLawyerByName(String name, Lawyer lawyer){
        return null;
    }
    Lawyer deleteLawyerById(String id){
        return null;
    }
    Lawyer deleteLawyerByName(String name){
        return null;
    }
    String deleteAlLawyers(){
        return null;
    }
}
