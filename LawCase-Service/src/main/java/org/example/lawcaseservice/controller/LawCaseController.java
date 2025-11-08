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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawcase")
public class LawCaseController {

    private final LawCaseService lawCaseService;

    ModelMapper modelMapper;

    private static final Logger LOGGER
            = LoggerFactory.getLogger(LawyerController.class);

    private final String NUMBER_VARIABLE_PATH = "lawyCaseId";
    private final String NAME_VARIABLE_PATH = "lawCaseName";

    @PostMapping
    ResponseEntity<LawCaseDTO> createLawyer(@Valid @RequestBody LawCaseDTO lawCaseDTO){
        LawCase added = lawCaseService.createLawCase(modelMapper.map(lawCaseDTO, LawCase.class));
        LOGGER.info("LawCase: {} was added tp the database!", added.getName());
        return new ResponseEntity<>(modelMapper.map(added,LawCaseDTO.class),
                HttpStatus.valueOf(201));
    }

    @GetMapping("/getById/{lawCaseId}")
    ResponseEntity<LawCaseDTO> getLawyerById(@PathVariable(NUMBER_VARIABLE_PATH) String lawCaseId) {
        LawCaseDTO foundedById = modelMapper.map(lawCaseService.getLawCaseById(lawCaseId),LawCaseDTO.class);
        LOGGER.info("LawCase: {} was founded by id in the database!", foundedById.getName());
        return new ResponseEntity<>(foundedById, HttpStatus.valueOf(200));
    }

    @GetMapping("/getByName") // ?lawyerName=
    ResponseEntity<LawCaseDTO> getLawyerByName(@RequestParam(NAME_VARIABLE_PATH) String lawCaseName) {
        LawCaseDTO foundedByName = modelMapper.map(lawCaseService.getLawCaseByName(lawCaseName),LawCaseDTO.class);
        LOGGER.info("LawCase: {} was founded by name in the database!", foundedByName.getName());
        return new ResponseEntity<>(foundedByName, HttpStatus.valueOf(200));
    }

    @GetMapping("/getAllLawCases")
    ResponseEntity<List<LawCaseDTO>> getAllLawyers() {
        List<LawCase> lawCases = lawCaseService.getAllLawCases();
        if (lawCases.isEmpty()) {
            throw new LayerInstantiationException("There is no lawCases in the database!");
        }
        LOGGER.info("All LawCases were founded!");
        List<LawCaseDTO> lawCaseDTOs = lawCases.stream()
                .map(lawCase -> modelMapper.map(lawCase,LawCaseDTO.class))
                .toList();
        return new ResponseEntity<>(lawCaseDTOs, HttpStatus.valueOf(200));
    }

}
