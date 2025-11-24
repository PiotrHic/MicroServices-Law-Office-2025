package org.example.lawclientservice.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.lawclientservice.domain.DTO.LawClientDTO;
import org.example.lawclientservice.domain.LawClient;
import org.example.lawclientservice.service.LawClientService;
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
@RequestMapping("/api/lawclient/create")
public class CreateController {

    private final LawClientService lawClientService;
    ModelMapper modelMapper;

    private static final Logger LOGGER
            = LoggerFactory.getLogger(WebClientController.class);

    @Operation(
            description = "Creates a new LawClient and stores it in the database"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "LawClient created successfully"),
            @ApiResponse(responseCode = "500", description = "Invalid input data")
    })
    @PostMapping
    ResponseEntity<LawClientDTO> createLawClient(@Valid @RequestBody LawClientDTO lawClientDTO){
        LawClient added = lawClientService.createLawClient(modelMapper.map(lawClientDTO,LawClient.class));
        LOGGER.info("LawClient: {} was added tp the database!", added.getName());
        return new ResponseEntity<>(modelMapper.map(added,LawClientDTO.class),
                HttpStatus.valueOf(201));
    }

}
