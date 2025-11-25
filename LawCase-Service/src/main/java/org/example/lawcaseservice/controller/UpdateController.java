package org.example.lawcaseservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.lawcaseservice.domain.DTO.LawCaseDTO;
import org.example.lawcaseservice.domain.LawCase;
import org.example.lawcaseservice.mapper.LawCaseMapper;
import org.example.lawcaseservice.service.LawCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawcase/update")
public class UpdateController {

    private final LawCaseService lawCaseService;
    private final LawCaseMapper lawCaseMapper;
    private static final Logger LOGGER
            = LoggerFactory.getLogger(WebClientController.class);

    private final String NUMBER_VARIABLE_PATH = "lawCaseId";
    private final String NUMBER_QUERYE_PATH = "/{lawCaseId}";
    @Operation(
            description = "Update LawCase from the database by the id  - /api/lawcase/update/byId/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase delivered successfully"),
            @ApiResponse(responseCode = "404", description = "LawCase was not found by id"),
            @ApiResponse(responseCode = "500", description = "Parameter was not correct")
    })
    @PutMapping("/byId"+ NUMBER_QUERYE_PATH)
    ResponseEntity<LawCaseDTO> updateLawCaseById(@PathVariable(NUMBER_VARIABLE_PATH) String lawCaseId,
                                                 @Valid @RequestBody LawCase lawCase) {
        LawCase updated = lawCaseService.updateLawCaseById(lawCaseId, lawCase);
        LawCaseDTO updatedDTO = lawCaseMapper.toDTO(updated);
        LOGGER.info("LawCase: {} was updated by id to the database!", lawCaseId);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Update LawCase from the database by the name  " +
                    "- /api/lawcase/update/byName?lawcaseName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase delivered successfully"),
            @ApiResponse(responseCode = "404", description = "LawCase was not found by name"),
            @ApiResponse(responseCode = "500", description = "Parameter was not correct")
    })
    @PutMapping("/byName") // ?lawyerName=
    ResponseEntity<LawCaseDTO> updateLawCaseByName(@RequestParam String lawCaseName, @Valid @RequestBody LawCaseDTO lawCaseDTO){
        LawCase toUpdate = lawCaseMapper.toEntity(lawCaseDTO);
        LawCase updated = lawCaseService.updateLawCaseByName(lawCaseName, toUpdate);
        LawCaseDTO updatedDTO = lawCaseMapper.toDTO(updated);
        LOGGER.info("LawCase: {} was updated by name to the database!", lawCaseName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }
}
