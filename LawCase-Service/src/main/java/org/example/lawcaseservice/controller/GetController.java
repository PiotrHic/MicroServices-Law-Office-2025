package org.example.lawcaseservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.lawcaseservice.client.LawClientClient;
import org.example.lawcaseservice.client.LawyerClient;
import org.example.lawcaseservice.domain.DTO.LawCaseDTO;
import org.example.lawcaseservice.domain.LawCase;
import org.example.lawcaseservice.service.LawCaseService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawcase/get")
public class GetController {

    private final LawCaseService lawCaseService;
    ModelMapper modelMapper;

    private static final Logger LOGGER
            = LoggerFactory.getLogger(WebClientController.class);

    private final String NUMBER_VARIABLE_PATH = "lawCaseId";
    private final String NUMBER_QUERY_PATH = "/{lawCaseId}";
    private final String NAME_VARIABLE_PATH = "lawCaseName";


    @Operation(
            description = "Get LawCase from the database by the id  - api/lawcase/get/byId/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase delivered successfully"),
            @ApiResponse(responseCode = "404", description = "LawCase was not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/byId" + NUMBER_QUERY_PATH)
    ResponseEntity<LawCaseDTO> getLawCaseById(@PathVariable(NUMBER_VARIABLE_PATH) String lawCaseId) {
        LawCaseDTO foundedById = modelMapper.map(lawCaseService.getLawCaseById(lawCaseId),LawCaseDTO.class);
        LOGGER.info("LawCase: {} was founded by id in the database!", foundedById.getName());
        return new ResponseEntity<>(foundedById, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Get LawCase from the database by the name  - api/lawcase/get/byName?lawcaseName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase delivered successfully"),
            @ApiResponse(responseCode = "404", description = "LawCase was not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/byName") // ?lawyerName=
    ResponseEntity<LawCaseDTO> getLawCaseByName(@RequestParam(NAME_VARIABLE_PATH) String lawCaseName) {
        LawCaseDTO foundedByName = modelMapper.map(lawCaseService.getLawCaseByName(lawCaseName),LawCaseDTO.class);
        LOGGER.info("LawCase: {} was founded by name in the database!", foundedByName.getName());
        return new ResponseEntity<>(foundedByName, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Get all LawCase from the Database - api/lawcase/get/allLawCases"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase delivered"),
            @ApiResponse(responseCode = "500", description = "Empty Database")
    })
    @GetMapping("/allLawCases")
    ResponseEntity<List<LawCaseDTO>> getAllLawCases() {
        List<LawCase> lawCases = lawCaseService.getAllLawCases();
        if (lawCases.isEmpty()) {
            throw new LayerInstantiationException("There is no LawCases in the database!");
        }
        LOGGER.info("All LawCases were founded!");
        List<LawCaseDTO> lawCaseDTOs = lawCases.stream()
                .map(lawCase -> modelMapper.map(lawCase,LawCaseDTO.class))
                .toList();
        return new ResponseEntity<>(lawCaseDTOs, HttpStatus.valueOf(200));
    }
}
