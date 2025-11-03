package org.example.lawyerservice.controller;

import lombok.AllArgsConstructor;
import org.example.lawyerservice.domain.DTO.LawyerDTO;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.service.LawyerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawyer")
public class LawyerController {

    private final LawyerService lawyerService;

    ModelMapper modelMapper = new ModelMapper();

    private static final Logger LOGGER
            = LoggerFactory.getLogger(LawyerController.class);

    private final String NUMBER_PATH = "{lawyerId}";
    private final String PATH_VARIABLE_PATH = "lawyerId";

    @GetMapping("/testMethod")
    ResponseEntity<LawyerDTO> testMethod() {
        Lawyer testLawyer = Lawyer.builder().id("1").name("testMethod").build();
        LawyerDTO responseDTO = modelMapper.map(testLawyer, LawyerDTO.class);
        ResponseEntity<LawyerDTO> response = new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(201));
        LOGGER.info("Test Method was requested!");
        return response;
    }
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
