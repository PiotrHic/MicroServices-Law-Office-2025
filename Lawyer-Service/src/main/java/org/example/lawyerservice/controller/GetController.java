package org.example.lawyerservice.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.example.lawyerservice.domain.DTO.LawyerDTO;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.mapper.LawyerMapper;
import org.example.lawyerservice.service.LawyerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawyer/get")
public class GetController {

    private final LawyerService lawyerService;
    private final LawyerMapper lawyerMapper;
    private static final Logger LOGGER
            = LoggerFactory.getLogger(WebClientController.class);

    private final String NUMBER_VARIABLE_PATH = "lawyerId";
    private final String NUMBER_QUERY_PATH = "/{lawyerId}";
    private final String NAME_VARIABLE_PATH = "lawyerName";

    @Operation(
            description = "Get Lawyer from the database by the id  - api/lawyer/get/byId/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer delivered successfully"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found by id")
    })
    @GetMapping("/byId" + NUMBER_QUERY_PATH)
    ResponseEntity<LawyerDTO> getLawyerById(@PathVariable(NUMBER_VARIABLE_PATH) String lawyerId) {
        LawyerDTO foundedById = lawyerMapper.toDTO(lawyerService.getLawyerByID(lawyerId));
        LOGGER.info("Lawyer: {} was founded by id in the database!", foundedById.getName());
        return new ResponseEntity<>(foundedById, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Get Lawyer from the database by the name  - api/lawyer/get/byName?lawyerName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer delivered successfully"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found by name"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/byName") // ?lawyerName=
    ResponseEntity<LawyerDTO> getLawyerByName(@RequestParam(NAME_VARIABLE_PATH) String lawyerName) {
        LawyerDTO foundedByName = lawyerMapper.toDTO(lawyerService.getLawyerByName(lawyerName));
        LOGGER.info("Lawyer: {} was founded by name in the database!", foundedByName.getName());
        return new ResponseEntity<>(foundedByName, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Get all Lawyers from the Database - api/lawyer/get/allLawyers"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyers delivered"),
            @ApiResponse(responseCode = "500", description = "Empty Database or Internal Server Error ")
    })
    @GetMapping("/allLawyers")
    ResponseEntity<List<LawyerDTO>> getAllLawyers() {
        List<Lawyer> lawyers = lawyerService.getAllLawyers();
        if (lawyers.isEmpty()) {
            throw new LayerInstantiationException("There is no lawyers in the database!");
        }
        LOGGER.info("All Lawyers were founded!");
        List<LawyerDTO> lawyersDTO = lawyers.stream()
                .map(lawyerMapper::toDTO)
                .toList();
        return new ResponseEntity<>(lawyersDTO, HttpStatus.valueOf(200));
    }

}
