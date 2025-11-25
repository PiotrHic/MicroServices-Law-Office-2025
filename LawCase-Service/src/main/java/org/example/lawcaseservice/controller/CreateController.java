package org.example.lawcaseservice.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.example.lawcaseservice.domain.DTO.LawCaseDTO;
import org.example.lawcaseservice.domain.LawCase;
import org.example.lawcaseservice.mapper.LawCaseMapper;
import org.example.lawcaseservice.service.LawCaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lawcase/create")
public class CreateController extends ParentController{


    public CreateController(LawCaseService lawCaseService, LawCaseMapper lawCaseMapper) {
        super(lawCaseService, lawCaseMapper);
    }

    @Operation(
            description = "Creates a new LawCase and stores it in the database - api/lawcase/create"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "LawCase created successfully"),
            @ApiResponse(responseCode = "500", description = "Invalid input data")
    })
    @PostMapping
    ResponseEntity<LawCaseDTO> createLawCase(@Valid @RequestBody LawCaseDTO lawCaseDTO){
        LawCase added = lawCaseService.createLawCase(lawCaseMapper.toEntity(lawCaseDTO));
        LOGGER.info("LawCase: {} was added tp the database!", added.getName());
        return new ResponseEntity<>(lawCaseMapper.toDTO(added),
                HttpStatus.valueOf(201));
    }
}
