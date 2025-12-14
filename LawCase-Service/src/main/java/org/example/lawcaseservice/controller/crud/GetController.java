package org.example.lawcaseservice.controller.crud;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.lawcaseservice.controller.ParentController;
import org.example.lawcaseservice.domain.DTO.LawCaseDTO;
import org.example.lawcaseservice.domain.LawCase;
import org.example.lawcaseservice.mapper.LawCaseMapper;
import org.example.lawcaseservice.mapper.LawyerMapper;
import org.example.lawcaseservice.service.LawCaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/lawcase/get")
public class GetController extends ParentController {

    public GetController(LawCaseService lawCaseService, LawCaseMapper lawCaseMapper, LawyerMapper lawyerMapper) {
        super(lawCaseService, lawCaseMapper, lawyerMapper);
    }

    @Operation(
            description = "Get LawCase from the database by the id  - api/lawcase/get/byId/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase delivered successfully"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @GetMapping("/byId" + LAWCASE_NUMBER_QUERY_PATH)
    ResponseEntity<LawCaseDTO> getLawCaseById(@PathVariable(LAWCASE_NUMBER_VARIABLE_PATH) String lawCaseId) {
        LawCaseDTO foundedById = lawCaseMapper.toDTO(lawCaseService.getLawCaseById(lawCaseId));
        LOGGER.info("LawCase: {} was founded by id in the database!", foundedById.getName());
        return new ResponseEntity<>(foundedById, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Get LawCase from the database by the name  - api/lawcase/get/byName?lawcaseName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase delivered successfully"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_NAME),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @GetMapping("/byName")
    ResponseEntity<LawCaseDTO> getLawCaseByName(@RequestParam(NAME_VARIABLE_PATH) String lawCaseName) {
        LawCaseDTO foundedByName = lawCaseMapper.toDTO(lawCaseService.getLawCaseByName(lawCaseName));
        LOGGER.info("LawCase: {} was founded by name in the database!", lawCaseName);
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
                .map(lawCaseMapper::toDTO)
                .toList();
        return new ResponseEntity<>(lawCaseDTOs, HttpStatus.valueOf(200));
    }
}
