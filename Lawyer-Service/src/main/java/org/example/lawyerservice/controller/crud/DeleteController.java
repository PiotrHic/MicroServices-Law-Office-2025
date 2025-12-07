package org.example.lawyerservice.controller.crud;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.lawyerservice.controller.ParentController;
import org.example.lawyerservice.domain.DTO.LawyerDTO;
import org.example.lawyerservice.mapper.LawCaseMapper;
import org.example.lawyerservice.mapper.LawyerMapper;
import org.example.lawyerservice.service.LawyerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lawyer/delete")
public class DeleteController extends ParentController {

    public DeleteController(LawyerService lawyerService, LawyerMapper lawyerMapper, LawCaseMapper lawCaseMapper) {
        super(lawyerService, lawyerMapper, lawCaseMapper);
    }

    @Operation(
            description = "Delete Lawyer from the database by the id - /api/lawyer/delete/byId/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer deleted successfully by id"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @DeleteMapping("/byId" + NUMBER_QUERY_PATH)
    ResponseEntity<LawyerDTO> deleteLawyerById(@PathVariable(NUMBER_VARIABLE_PATH) String lawyerId){
        LawyerDTO deleted = lawyerMapper.toDTO(lawyerService.deleteLawyerById(lawyerId));
        LOGGER.info("Lawyer with id {} : deleted by id from the database!", deleted.getName());
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @Operation(
            description = "Delete Lawyer from the database by the name  - /api/lawyer/delete/byName" +
                    "?lawyerName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer deleted successfully by name"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_NAME),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @DeleteMapping("/byName")
    ResponseEntity <LawyerDTO> deleteLawyerByName(@RequestParam String lawyerName){
        LawyerDTO updatedDTO = lawyerMapper.toDTO(lawyerService.deleteLawyerByName(lawyerName));
        LOGGER.info("Lawyer with name: {} was deleted from the database!", lawyerName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Delete all Lawyers from the Database"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All Lawyers deleted"),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_SHORT)
    })
    @DeleteMapping("/allLawyers")
    ResponseEntity <String> deleteAlLawyers(){
        lawyerService.deleteAlLawyers();
        LOGGER.info("Database is empty");
        return new ResponseEntity<>("Database is empty", HttpStatus.OK);
    }
}
