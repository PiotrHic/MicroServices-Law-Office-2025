package org.example.lawyerservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.example.lawyerservice.domain.DTO.LawyerDTO;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.service.LawyerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/lawyer/delete")
public class DeleteController {

    private final LawyerService lawyerService;
    private final ModelMapper modelMapper;

    private static final Logger LOGGER
            = LoggerFactory.getLogger(WebClientController.class);

    private final String NUMBER_VARIABLE_PATH = "lawyerId";
    private final String NUMBER_QUERY_PATH = "/{lawyerId}";

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
        LawyerDTO deleted = modelMapper.map(lawyerService.deleteLawyerById(lawyerId), LawyerDTO.class);
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
        Lawyer deleted= lawyerService.deleteLawyerByName(lawyerName);
        LawyerDTO updatedDTO = modelMapper.map(deleted, LawyerDTO.class);
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
