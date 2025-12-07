package org.example.lawcaseservice.controller.crud;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.example.lawcaseservice.controller.ParentController;
import org.example.lawcaseservice.domain.DTO.LawCaseDTO;
import org.example.lawcaseservice.domain.LawCase;
import org.example.lawcaseservice.mapper.LawCaseMapper;
import org.example.lawcaseservice.mapper.LawyerMapper;
import org.example.lawcaseservice.service.LawCaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lawcase/update")
public class UpdateController extends ParentController {


    public UpdateController(LawCaseService lawCaseService, LawCaseMapper lawCaseMapper, LawyerMapper lawyerMapper) {
        super(lawCaseService, lawCaseMapper, lawyerMapper);
    }

    @Operation(
            description = "Update LawCase from the database by the id  - /api/lawcase/update/byId/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase updated successfully by id"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @PutMapping("/byId"+ LAWCASE_NUMBER_QUERY_PATH)
    ResponseEntity<LawCaseDTO> updateLawCaseById(@PathVariable(LAWCASE_NUMBER_VARIABLE_PATH) String lawCaseId,
                                                 @Valid @RequestBody LawCase lawCase) {
        LawCase updated = lawCaseService.updateLawCaseById(lawCaseId, lawCase);
        LOGGER.info("LawCase with id: {} was updated by id to the database!", lawCaseId);
        return new ResponseEntity<>(lawCaseMapper.toDTO(updated), HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Update LawCase from the database by the name  " +
                    "- /api/lawcase/update/byName?lawcaseName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase updated successfully by name"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_NAME),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @PutMapping("/byName") // ?lawCaseName=
    ResponseEntity<LawCaseDTO> updateLawCaseByName(@RequestParam String lawCaseName,
                                                   @Valid @RequestBody LawCaseDTO lawCaseDTO){
        LawCase toUpdate = lawCaseMapper.toEntity(lawCaseDTO);
        LawCase updated = lawCaseService.updateLawCaseByName(lawCaseName, toUpdate);
        LOGGER.info("LawCase with name: {} was updated by name to the database!", lawCaseName);
        return new ResponseEntity<>(lawCaseMapper.toDTO(updated), HttpStatus.valueOf(200));
    }
}
