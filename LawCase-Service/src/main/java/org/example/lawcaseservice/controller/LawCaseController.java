package org.example.lawcaseservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.lawcaseservice.client.LawClientClient;
import org.example.lawcaseservice.client.LawyerClient;
import org.example.lawcaseservice.domain.DTO.LawCaseDTO;
import org.example.lawcaseservice.domain.LawCase;
import org.example.lawcaseservice.service.LawCaseService;
import org.example.lawyerservice.exception.LawyerNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawcase")
public class LawCaseController {

    private final LawCaseService lawCaseService;

    ModelMapper modelMapper;

    private LawyerClient lawyerClient;
    private LawClientClient lawClientClient;

    private static final Logger LOGGER
            = LoggerFactory.getLogger(LawCaseController.class);

    private final String NUMBER_VARIABLE_PATH = "lawCaseId";
    private final String NAME_VARIABLE_PATH = "lawCaseName";

    @PostMapping
    ResponseEntity<LawCaseDTO> createLawCase(@Valid @RequestBody LawCaseDTO lawCaseDTO){
        LawCase added = lawCaseService.createLawCase(modelMapper.map(lawCaseDTO, LawCase.class));
        LOGGER.info("LawCase: {} was added tp the database!", added.getName());
        return new ResponseEntity<>(modelMapper.map(added,LawCaseDTO.class),
                HttpStatus.valueOf(201));
    }

    @GetMapping("/getById/{lawCaseId}")
    ResponseEntity<LawCaseDTO> getLawCaseById(@PathVariable(NUMBER_VARIABLE_PATH) String lawCaseId) {
        LawCaseDTO foundedById = modelMapper.map(lawCaseService.getLawCaseById(lawCaseId),LawCaseDTO.class);
        LOGGER.info("LawCase: {} was founded by id in the database!", foundedById.getName());
        return new ResponseEntity<>(foundedById, HttpStatus.valueOf(200));
    }

    @GetMapping("/getByName") // ?lawyerName=
    ResponseEntity<LawCaseDTO> getLawCaseByName(@RequestParam(NAME_VARIABLE_PATH) String lawCaseName) {
        LawCaseDTO foundedByName = modelMapper.map(lawCaseService.getLawCaseByName(lawCaseName),LawCaseDTO.class);
        LOGGER.info("LawCase: {} was founded by name in the database!", foundedByName.getName());
        return new ResponseEntity<>(foundedByName, HttpStatus.valueOf(200));
    }

    @GetMapping("/getAllLawCases")
    ResponseEntity<List<LawCaseDTO>> getAllLawCases() {
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

    @PutMapping("/updateById/{lawCaseId}")
    ResponseEntity<LawCaseDTO> updateLawCaseById(@PathVariable(NUMBER_VARIABLE_PATH) String lawCaseId,
                                               @Valid @RequestBody LawCase lawCase) {
        LawCase updated = lawCaseService.updateLawCaseById(lawCaseId, lawCase);
        LawCaseDTO updatedDTO = modelMapper.map(updated, LawCaseDTO.class);
        LOGGER.info("LawCase: {} was updated by id to the database!", lawCaseId);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @PutMapping("/updateByName") // ?lawyerName=
    ResponseEntity<LawCaseDTO> updateLawCaseByName(@RequestParam String lawCaseName,@Valid @RequestBody LawCaseDTO lawCaseDTO){
        LawCase toUpdate = modelMapper.map(lawCaseDTO, LawCase.class);
        LawCase updated = lawCaseService.updateLawCaseByName(lawCaseName, toUpdate);
        LawCaseDTO updatedDTO = modelMapper.map(updated, LawCaseDTO.class);
        LOGGER.info("LawCase: {} was updated by name to the database!", lawCaseName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @DeleteMapping("/deleteById/{lawCaseId}")
    ResponseEntity <LawCaseDTO> deleteLawCaseById(@PathVariable(NUMBER_VARIABLE_PATH) String lawCaseId){
        LawCaseDTO deleted = modelMapper.map(lawCaseService.deleteLawCaseById(lawCaseId), LawCaseDTO.class);
        LOGGER.info("LawCase deleted: {} by id from the database!", deleted.getName());
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @DeleteMapping("/deleteByName") // ?lawyerName=
    ResponseEntity <LawCaseDTO> deleteLawCaseByName(@RequestParam String lawCaseName){
        LawCase deleted= lawCaseService.deleteLawCaseByName(lawCaseName);
        LawCaseDTO updatedDTO = modelMapper.map(deleted, LawCaseDTO.class);
        LOGGER.info("LawCase: {} was deleted by name from the database!", lawCaseName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @DeleteMapping("/deleteAll")
    ResponseEntity <String> deleteAlLawCases(){
        lawCaseService.deleteAllLawCases();
        LOGGER.info("Database is empty");
        return new ResponseEntity<>("Database is empty", HttpStatus.OK);
    }

    // WebClient Lawyer methods

    @GetMapping("/toBringLawyer/{lawyerId}")
    public LawCase findLawyerByLawyerId(@PathVariable("lawyerId") String lawyerId) {
        LawCase founded = lawCaseService
                .getAllLawCases()
                .stream()
                .filter(lawCase -> lawCase.getLawyerId()
                        .equals(lawyerId)).findFirst().orElseThrow();
        founded.setLawyer(lawyerClient.findLawyerByLawyerId(lawyerId));
        return founded;
    }

    // WebClient merhods

    @GetMapping("/forLawClient-withLawyer/{lawClientId}")
    public List<LawCase> findLawCaseWithLawyerByLawClientId(@PathVariable("lawClientId") String lawClientId){
        List<LawCase> lawCases = findLawCaseByLawClientId(lawClientId);
        lawCases
                .forEach(lawCase -> lawCase.setLawyer
                        (lawyerClient.findLawyerByLawyerId
                                (lawCase.getLawyerId())));
        return lawCases;
    }

    @GetMapping("forLawClient/{lawClientId}")
    public List<LawCase> findLawCaseByLawClientId(@PathVariable("lawClientId") String lawClientId){
        List<LawCase> lawCases
                = lawCaseService.getAllLawCases();
        return lawCases
                .stream()
                .filter(lawCase -> lawCase.getLawClientId().equals(lawClientId))
                .toList();
    }
}
