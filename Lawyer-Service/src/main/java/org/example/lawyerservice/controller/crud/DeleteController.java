package org.example.lawyerservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.lawyerservice.domain.DTO.LawyerDTO;
import org.example.lawyerservice.mapper.LawyerMapper;
import org.example.lawyerservice.service.LawyerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/lawyer/delete")
public class DeleteController extends ParentController{

    public DeleteController(LawyerService lawyerService, LawyerMapper lawyerMapper) {
        super(lawyerService, lawyerMapper);
    }

    @Operation(
            description = "Delete Lawyer from the database by the id  - /api/lawyer/delete/byId/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found by id"),
            @ApiResponse(responseCode = "500", description = "Parameter was not correct or Internal Server Error")
    })
    @DeleteMapping("/byId" + NUMBER_QUERY_PATH)
    ResponseEntity<LawyerDTO> deleteLawyerById(@PathVariable(NUMBER_VARIABLE_PATH) String lawyerId){
        LawyerDTO deleted = lawyerMapper.toDTO(lawyerService.deleteLawyerById(lawyerId));
        LOGGER.info("Lawyer deleted: {} by id from the database!", deleted.getName());
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @Operation(
            description = "Delete Lawyer from the database by the name  - /api/lawyer/delete/byName" +
                    "?lawyerName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found by name"),
            @ApiResponse(responseCode = "500", description = "Parameter was not correct or Internal Server Error")
    })
    @DeleteMapping("/byName") // ?lawyerName=
    ResponseEntity <LawyerDTO> deleteLawyerByName(@RequestParam String lawyerName){
        LawyerDTO updatedDTO = lawyerMapper.toDTO(lawyerService.deleteLawyerByName(lawyerName));
        LOGGER.info("Lawyer: {} was deleted from the database!", lawyerName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Delete all Lawyers from the Database"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All Lawyers deleted")
    })
    @DeleteMapping("/allLawyers")
    ResponseEntity <String> deleteAlLawyers(){
        lawyerService.deleteAlLawyers();
        LOGGER.info("Database is empty");
        return new ResponseEntity<>("Database is empty", HttpStatus.OK);
    }
}
