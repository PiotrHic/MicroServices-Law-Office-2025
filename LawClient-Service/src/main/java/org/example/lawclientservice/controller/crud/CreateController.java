package org.example.lawclientservice.controller.crud;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.example.lawclientservice.controller.ParentController;
import org.example.lawclientservice.domain.DTO.LawClientDTO;
import org.example.lawclientservice.domain.LawClient;
import org.example.lawclientservice.mapper.LawClientMapper;
import org.example.lawclientservice.service.LawClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lawclient/create")
public class CreateController extends ParentController {

    public CreateController(LawClientService lawClientService, LawClientMapper lawClientMapper) {
        super(lawClientService, lawClientMapper);
    }

    @Operation(
            description = "Creates a new LawClient and stores it in the database"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "LawClient created successfully"),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @PostMapping
    ResponseEntity<LawClientDTO> createLawClient(@Valid @RequestBody LawClientDTO lawClientDTO){
        LawClient added = lawClientService.createLawClient(lawClientMapper.toEntity(lawClientDTO));
        LOGGER.info("LawClient: {} was added tp the database!", added.getName());
        return new ResponseEntity<>(lawClientMapper.toDTO(added),
                HttpStatus.valueOf(201));
    }

}
