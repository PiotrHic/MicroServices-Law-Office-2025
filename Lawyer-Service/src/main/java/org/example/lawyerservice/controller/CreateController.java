package org.example.lawyerservice.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.example.lawyerservice.domain.DTO.LawyerDTO;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.mapper.LawyerMapper;
import org.example.lawyerservice.service.LawyerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lawyer/create")
public class CreateController extends ParentController{


    public CreateController(LawyerService lawyerService, LawyerMapper lawyerMapper) {
        super(lawyerService, lawyerMapper);
    }

    @Operation(
            description = "Creates a new Lawyer and stores it in the database  - api/lawyer/create"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Lawyer created successfully"),
            @ApiResponse(responseCode = "500", description = "Invalid input data or some server error")
    })
    @PostMapping
    ResponseEntity<LawyerDTO> createLawyer(@Valid @RequestBody LawyerDTO lawyerDTO){
        Lawyer added = lawyerService.addLawyer(lawyerMapper.toEntity(lawyerDTO));
        LawyerDTO dto = lawyerMapper.toDTO(added);
        LOGGER.info("Lawyer: {} was added tp the database!", added.getName());
        return new ResponseEntity<>(dto,HttpStatus.valueOf(201));
    }
}
