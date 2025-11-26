package org.example.lawclientservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.lawclientservice.domain.DTO.LawClientDTO;
import org.example.lawclientservice.domain.LawClient;
import org.example.lawclientservice.mapper.LawClientMapper;
import org.example.lawclientservice.service.LawClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lawclient/get")
public class GetController extends ParentController{

    public GetController(LawClientService lawClientService, LawClientMapper lawClientMapper) {
        super(lawClientService, lawClientMapper);
    }

    @Operation(
            description = "Get LawClient from the database by the id  - api/lawclient/get/byId/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient delivered successfully"),
            @ApiResponse(responseCode = "404", description = "LawClient was not found by id"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/byId"+ NUMBER_QUERY_PATH)
    ResponseEntity<LawClientDTO> getLawClientById(@PathVariable(NUMBER_VARIABLE_PATH) String lawClientId) {
        LawClientDTO foundedById = lawClientMapper.toDTO(lawClientService.getLawClientByID(lawClientId));
        LOGGER.info("LawClient: {} was founded by id in the database!", foundedById.getName());
        return new ResponseEntity<>(foundedById, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Get LawClient from the database by the name  - api/lawclient/byName?lawclientName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient delivered successfully"),
            @ApiResponse(responseCode = "404", description = "LawClient was not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/byName") // ?lawyerName=
    ResponseEntity<LawClientDTO> getLawClientByName(@RequestParam(NAME_VARIABLE_PATH) String lawClientName) {
        LawClientDTO foundedByName = lawClientMapper.toDTO(lawClientService.getLawClientByName(lawClientName));
        LOGGER.info("LawClient: {} was founded by name in the database!", foundedByName.getName());
        return new ResponseEntity<>(foundedByName, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Get all LawClients from the Database - api/lawclient/get/allLawClients"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClients delivered"),
            @ApiResponse(responseCode = "500", description = "Empty Database"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/allLawClients")
    ResponseEntity<List<LawClientDTO>> getAllLawClients() {
        List<LawClient> lawClients = lawClientService.getAllLawClients();
        if (lawClients.isEmpty()) {
            throw new LayerInstantiationException("There is no law clients in the database!");
        }
        LOGGER.info("All law clients were founded!");
        List<LawClientDTO> lawClientsDTOs = lawClients.stream()
                .map(lawClientMapper::toDTO)
                .toList();
        return new ResponseEntity<>(lawClientsDTOs, HttpStatus.valueOf(200));
    }
}
