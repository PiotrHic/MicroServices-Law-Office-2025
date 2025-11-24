package org.example.lawyerservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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
@RequestMapping("/api/lawyer/update")
public class UpdateController {

    private final LawyerService lawyerService;
    private final ModelMapper modelMapper;

    private static final Logger LOGGER
            = LoggerFactory.getLogger(WebClientController.class);

    private final String NUMBER_VARIABLE_PATH = "lawyerId";
    private final String NUMBER_QUERY_PATH = "/{lawyerId}";

    @Operation(
            description = "Update Lawyer from the database by the id  - /api/lawyer/update/byId/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer delivered successfully"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found by name"),
            @ApiResponse(responseCode = "500", description = "Parameter was not correct or Internal Server Error")
    })
    @PutMapping("/byId" + NUMBER_QUERY_PATH)
    ResponseEntity<LawyerDTO> updateLawyerById(@PathVariable(NUMBER_VARIABLE_PATH) String lawyerId,
                                               @Valid @RequestBody LawyerDTO lawyerDTO) {
        Lawyer toUpdate = modelMapper.map(lawyerDTO, Lawyer.class);
        Lawyer updated = lawyerService.updateLawyerById(lawyerId, toUpdate);
        LawyerDTO updatedDTO = modelMapper.map(updated, LawyerDTO.class);
        LOGGER.info("Lawyer: {} was updated by id to the database!", lawyerId);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Update Lawyer from the database by the name  " +
                    "- /api/lawyer/update/byName?lawyerName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer delivered successfully"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found by name"),
            @ApiResponse(responseCode = "500", description = "Parameter was not correct or Internal Server Error"),
    })
    @PutMapping("/byName") // ?lawyerName=
    ResponseEntity<LawyerDTO> updateLawyerByName(@RequestParam String lawyerName,@Valid @RequestBody LawyerDTO lawyerDTO){
        Lawyer toUpdate = modelMapper.map(lawyerDTO, Lawyer.class);
        Lawyer updated = lawyerService.updateLawyerByName(lawyerName, toUpdate);
        LawyerDTO updatedDTO = modelMapper.map(updated, LawyerDTO.class);
        LOGGER.info("Lawyer: {} was updated by name to the database!", lawyerName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }
}
