package org.example.lawyerservice.controller.crud;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.example.lawyerservice.controller.ParentController;
import org.example.lawyerservice.domain.DTO.LawyerDTO;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.mapper.LawCaseMapper;
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
public class CreateController extends ParentController {

    public CreateController(LawyerService lawyerService, LawyerMapper lawyerMapper, LawCaseMapper lawCaseMapper) {
        super(lawyerService, lawyerMapper, lawCaseMapper);
    }

    @Operation(
            description = "Creates a new Lawyer and stores it in the database - api/lawyer/create"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Lawyer created successfully"),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @PostMapping
    ResponseEntity<LawyerDTO> createLawyer(@Valid @RequestBody LawyerDTO lawyerDTO){
        Lawyer added = lawyerService.addLawyer(lawyerMapper.toEntity(lawyerDTO));
        LOGGER.info("Lawyer with id: {} was added tp the database!", added.getId());
        return new ResponseEntity<>(lawyerMapper.toDTO(added),HttpStatus.valueOf(201));
    }
}
