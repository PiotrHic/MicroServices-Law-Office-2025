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

@RestController
@RequestMapping("/api/lawcase/delete")
public class DeleteController extends ParentController {


    public DeleteController(LawCaseService lawCaseService, LawCaseMapper lawCaseMapper, LawyerMapper lawyerMapper) {
        super(lawCaseService, lawCaseMapper, lawyerMapper);
    }

    @Operation(
            description = "Delete LawCase from the database by the id  - /api/lawcase/delete/byId/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase deleted successfully by id"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @DeleteMapping("/byId" + LAWCASE_NUMBER_QUERY_PATH)
    ResponseEntity<LawCaseDTO> deleteLawCaseById(@PathVariable(LAWCASE_NUMBER_VARIABLE_PATH) String lawCaseId){
        LawCaseDTO deleted = lawCaseMapper.toDTO(lawCaseService.deleteLawCaseById(lawCaseId));
        LOGGER.info("LawCase deleted by id: {} by id from the database!", lawCaseId);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @Operation(
            description = "Delete LawCase from the database by the name  - /api/lawcase/delete/byName" +
                    "?lawcaseName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase deleted successfully by name"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_NAME),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @DeleteMapping("/byName")
    ResponseEntity <LawCaseDTO> deleteLawCaseByName(@RequestParam String lawCaseName){
        LawCase deleted = lawCaseService.deleteLawCaseByName(lawCaseName);
        LawCaseDTO updatedDTO = lawCaseMapper.toDTO(deleted);
        LOGGER.info("LawCase by name: {} was deleted by name from the database!", lawCaseName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Delete all LawCases from the Database  /api/lawcase/delete/byName" +
                    "?lawcaseName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All LawCases deleted"),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_SHORT)
    })
    @DeleteMapping("/allLawCases")
    ResponseEntity <String> deleteAlLawCases(){
        lawCaseService.deleteAllLawCases();
        LOGGER.info("Database is empty");
        return new ResponseEntity<>("Database is empty", HttpStatus.OK);
    }
}
