package org.example.lawcaseservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.lawcaseservice.domain.DTO.LawCaseDTO;
import org.example.lawcaseservice.domain.LawCase;
import org.example.lawcaseservice.service.LawCaseService;
import org.example.lawyerservice.controller.LawyerController;
import org.example.lawyerservice.domain.DTO.LawyerDTO;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.service.LawyerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawcase")
public class LawCaseController {

    private final LawCaseService lawCaseService;

    ModelMapper modelMapper = new ModelMapper();

    private static final Logger LOGGER
            = LoggerFactory.getLogger(LawyerController.class);

    @PostMapping
    ResponseEntity<LawCaseDTO> createLawyer(@Valid @RequestBody LawCaseDTO lawCaseDTO){
        LawCase added = lawCaseService.createLawCase(modelMapper.map(lawCaseDTO, LawCase.class));
        LOGGER.info("LawCase: {} was added tp the database!", added.getName());
        return new ResponseEntity<>(modelMapper.map(added,LawCaseDTO.class),
                HttpStatus.valueOf(201));
    }

}
