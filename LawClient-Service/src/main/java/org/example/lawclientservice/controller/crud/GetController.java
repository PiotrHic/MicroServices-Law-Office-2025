package org.example.lawclientservice.controller.crud;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.lawclientservice.controller.ParentController;
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
public class GetController extends ParentController {

    public GetController(LawClientService lawClientService, LawClientMapper lawClientMapper) {
        super(lawClientService, lawClientMapper);
    }

    @Operation(
            description = "Get LawClient from the database by the id  - api/lawclient/get/byId/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient delivered successfully by id"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @GetMapping("/byId"+ NUMBER_QUERY_PATH)
    ResponseEntity<LawClientDTO> getLawClientById(@PathVariable(NUMBER_VARIABLE_PATH) String lawClientId) {
        LawClientDTO foundedByIdDTO = lawClientMapper.toDTO(lawClientService.getLawClientByID(lawClientId));
        LOGGER.info("LawClient with id: {} was founded by id in the database!", foundedByIdDTO.getId());
        return new ResponseEntity<>(foundedByIdDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Get LawClient from the database by the name  - api/lawclient/byName?lawclientName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient delivered successfully by name"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_NAME),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @GetMapping("/byName") //
    ResponseEntity<LawClientDTO> getLawClientByName(@RequestParam(NAME_VARIABLE_PATH) String lawClientName) {
        LawClientDTO foundedByNameDTO = lawClientMapper.toDTO(lawClientService.getLawClientByName(lawClientName));
        LOGGER.info("LawClient with name: {} was founded by name in the database!", foundedByNameDTO.getName());
        return new ResponseEntity<>(foundedByNameDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Get all LawClients from the Database - api/lawclient/get/allLawClients"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClients delivered"),
            @ApiResponse(responseCode = "500", description = "Empty Database or " + DESCRIPTION_500_SHORT)
    })
    @GetMapping("/allLawClients")
    ResponseEntity<List<LawClientDTO>> getAllLawClients() {
        List<LawClient> lawClients = lawClientService.getAllLawClients();
        if (lawClients.isEmpty()) {
            throw new LayerInstantiationException("There is no LawClients in the database!");
        }
        LOGGER.info("All LawClients were founded!");
        List<LawClientDTO> lawClientsDTOs = lawClients.stream()
                .map(lawClientMapper::toDTO)
                .toList();
        return new ResponseEntity<>(lawClientsDTOs, HttpStatus.valueOf(200));
    }
}
