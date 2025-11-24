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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawyer/create")
public class CreateController {

    private final LawyerService lawyerService;

    private final ModelMapper modelMapper;

    private static final Logger LOGGER
            = LoggerFactory.getLogger(WebClientController.class);

    @Operation(
            description = "Creates a new Lawyer and stores it in the database  - api/lawyer/create"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Lawyer created successfully"),
            @ApiResponse(responseCode = "500", description = "Invalid input data or some server error")
    })
    @PostMapping
    ResponseEntity<LawyerDTO> createLawyer(@Valid @RequestBody LawyerDTO lawyerDTO){
        Lawyer added = lawyerService.addLawyer(modelMapper.map(lawyerDTO,Lawyer.class));
        LOGGER.info("Lawyer: {} was added tp the database!", added.getName());
        return new ResponseEntity<>(modelMapper.map(added,LawyerDTO.class),
                HttpStatus.valueOf(201));
    }
}
