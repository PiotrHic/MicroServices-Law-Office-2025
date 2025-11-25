package org.example.lawcaseservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.lawcaseservice.domain.DTO.LawCaseDTO;
import org.example.lawcaseservice.domain.LawCase;
import org.example.lawcaseservice.mapper.LawCaseMapper;
import org.example.lawcaseservice.service.LawCaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/lawcase/delete")
public class DeleteController extends ParentController{

    public DeleteController(LawCaseService lawCaseService, LawCaseMapper lawCaseMapper) {
        super(lawCaseService, lawCaseMapper);
    }

    @Operation(
            description = "Delete LawCase from the database by the id  - /api/lawcase/delete/byId/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase deleted successfully"),
            @ApiResponse(responseCode = "404", description = "LawCase was not found by id"),
            @ApiResponse(responseCode = "500", description = "Parameter is not correct or Internal Server Error")
    })
    @DeleteMapping("/byId" + NUMBER_QUERY_PATH)
    ResponseEntity<LawCaseDTO> deleteLawCaseById(@PathVariable(NUMBER_VARIABLE_PATH) String lawCaseId){
        LawCaseDTO deleted = lawCaseMapper.toDTO(lawCaseService.deleteLawCaseById(lawCaseId));
        LOGGER.info("LawCase deleted: {} by id from the database!", deleted.getName());
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @Operation(
            description = "Delete LawCase from the database by the name  - /api/lawcase/delete/byName" +
                    "?lawcaseName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase deleted successfully"),
            @ApiResponse(responseCode = "404", description = "LawCase was not found by name"),
            @ApiResponse(responseCode = "500", description = "Parameter is not correct or Internal Server Error")
    })
    @DeleteMapping("/byName")
    ResponseEntity <LawCaseDTO> deleteLawCaseByName(@RequestParam String lawCaseName){
        LawCase deleted= lawCaseService.deleteLawCaseByName(lawCaseName);
        LawCaseDTO updatedDTO = lawCaseMapper.toDTO(deleted);
        LOGGER.info("LawCase: {} was deleted by name from the database!", lawCaseName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Delete all LawCases from the Database  /api/lawcase/delete/byName" +
                    "?lawcaseName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All LawCases deleted"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/allLawCases")
    ResponseEntity <String> deleteAlLawCases(){
        lawCaseService.deleteAllLawCases();
        LOGGER.info("Database is empty");
        return new ResponseEntity<>("Database is empty", HttpStatus.OK);
    }
}
