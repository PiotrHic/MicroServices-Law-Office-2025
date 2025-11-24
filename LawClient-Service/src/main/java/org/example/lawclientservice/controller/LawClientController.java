package org.example.lawclientservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.lawclientservice.client.LawCaseClient;
import org.example.lawclientservice.domain.DTO.LawClientDTO;
import org.example.lawclientservice.domain.LawClient;
import org.example.lawclientservice.service.LawClientService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawclient")
@Tag(name = "LawClient Controller")
public class LawClientController {

    private final LawClientService lawClientService;

    ModelMapper modelMapper;

    private static final Logger LOGGER
            = LoggerFactory.getLogger(LawClientController.class);

    private final String NUMBER_VARIABLE_PATH = "lawClientId";
    private final String NAME_VARIABLE_PATH = "lawClientName";

    private final LawCaseClient lawCaseClient;

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

    @Operation(
            description = "Get LawClient from the database by the id  - api/lawclient/getById/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient delivered successfully"),
            @ApiResponse(responseCode = "404", description = "LawClient was not found by id")
    })
    @GetMapping("/getById/{lawClientId}")
    ResponseEntity<LawClientDTO> getLawClientById(@PathVariable(NUMBER_VARIABLE_PATH) String lawClientId) {
        LawClientDTO foundedById = modelMapper.map(lawClientService.getLawClientByID(lawClientId),LawClientDTO.class);
        LOGGER.info("LawClient: {} was founded by id in the database!", foundedById.getName());
        return new ResponseEntity<>(foundedById, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Get LawClient from the database by the name  - api/lawclient/getByName?lawclientName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient delivered successfully"),
            @ApiResponse(responseCode = "404", description = "LawClient was not found")
    })
    @GetMapping("/getByName") // ?lawyerName=
    ResponseEntity<LawClientDTO> getLawClientByName(@RequestParam(NAME_VARIABLE_PATH) String lawClientName) {
        LawClientDTO foundedByName = modelMapper.map(lawClientService.getLawClientByName(lawClientName),LawClientDTO.class);
        LOGGER.info("LawClient: {} was founded by name in the database!", foundedByName.getName());
        return new ResponseEntity<>(foundedByName, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Get all LawClients from the Database"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClients delivered"),
            @ApiResponse(responseCode = "500", description = "Empty Database")
    })
    @GetMapping("/getAllLawClients")
    ResponseEntity<List<LawClientDTO>> getAllLawClients() {
        List<LawClient> lawClients = lawClientService.getAllLawClients();
        if (lawClients.isEmpty()) {
            throw new LayerInstantiationException("There is no law clients in the database!");
        }
        LOGGER.info("All law clients were founded!");
        List<LawClientDTO> lawClientsDTOs = lawClients.stream()
                .map(lawClient -> modelMapper.map(lawClient,LawClientDTO.class))
                .toList();
        return new ResponseEntity<>(lawClientsDTOs, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Update LawClient from the database by the id  - /api/lawclient/updateById/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient delivered successfully"),
            @ApiResponse(responseCode = "404", description = "LawClient was not found by id"),
            @ApiResponse(responseCode = "500", description = "Parameter was not correct"),
    })
    @PutMapping("/updateById/{lawClientId}")
    ResponseEntity<LawClientDTO> updateLawClientById(@PathVariable(NUMBER_VARIABLE_PATH) String lawClientId,
                                               @Valid @RequestBody LawClientDTO lawClientDTO) {
        LawClient toUpdate = modelMapper.map(lawClientDTO, LawClient.class);
        LawClient updated = lawClientService.updateLawClientById(lawClientId, toUpdate);
        LawClientDTO updatedDTO = modelMapper.map(updated, LawClientDTO.class);
        LOGGER.info("LawClient: {} was updated by id to the database!", lawClientId);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Update LawClient from the database by the name  " +
                    "- /api/lawclient/updateByName?lawclientName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient delivered successfully"),
            @ApiResponse(responseCode = "404", description = "LawClient was not found by name"),
            @ApiResponse(responseCode = "500", description = "Parameter was not correct"),
    })
    @PutMapping("/updateByName") // ?lawyerName=
    ResponseEntity<LawClientDTO> updateLawClientByName(@RequestParam String lawClientName,@Valid
    @RequestBody LawClientDTO lawClientDTO){
        LawClient toUpdate = modelMapper.map(lawClientDTO, LawClient.class);
        LawClient updated = lawClientService.updateLawClientByName(lawClientName, toUpdate);
        LawClientDTO updatedDTO = modelMapper.map(updated, LawClientDTO.class);
        LOGGER.info("LawClient: {} was updated by name to the database!", lawClientName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Delete LawClient from the database by the id  - /api/lawclient/deleteById/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "LawClient was not found by id")
    })
    @DeleteMapping("/deleteById/{lawClientId}")
    ResponseEntity <LawClientDTO> deleteLawClientById(@PathVariable(NUMBER_VARIABLE_PATH) String lawClientId){
        LawClientDTO deleted = modelMapper.map(lawClientService.deleteLawClientById(lawClientId), LawClientDTO.class);
        LOGGER.info("LawClient deleted: {} by id from the database!", deleted.getName());
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @Operation(
            description = "Delete LawClient from the database by the name  - deleteByName?lawclientName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "LawClient was not found by name")
    })
    @DeleteMapping("/deleteByName") // ?lawyerName=
    ResponseEntity <LawClientDTO> deleteLawClientByName(@RequestParam String lawClientName){
        LawClient deleted = lawClientService.deleteLawClientByName(lawClientName);
        LawClientDTO updatedDTO = modelMapper.map(deleted, LawClientDTO.class);
        LOGGER.info("LawClient: {} was deleted by name from the database!", lawClientName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Delete all LawClients from the Database"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All LawClients deleted")
    })
    @DeleteMapping("/deleteAll")
    ResponseEntity <String> deleteAllLawClients(){
        lawClientService.deleteAllLawClients();
        LOGGER.info("Database is empty");
        return new ResponseEntity<>("Database is empty", HttpStatus.OK);
    }

    // WebClient methods

    //to LawCase
    @Operation(
            description = "Send LawClient to the LawCase microservice"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("forLawCase/{lawClientId}")
    public LawClient findLawClientByLawClientId(@PathVariable("lawClientId") String lawClientId){
        return lawClientService.getLawClientByID(lawClientId);
    }

    // to get resourse from another services

    @Operation(
            description = "To get LawCases by LawClient id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the LawClient"),
            @ApiResponse(responseCode = "404", description = "LawClient was not found"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/toBringLawCase/" + "{lawClientId}")
    public LawClient findLawCaseByLawClientId(@PathVariable("lawClientId") String lawClientId){
        LawClient founded = lawClientService.getLawClientByID(lawClientId);
        founded.setLawCaseList(lawCaseClient.findLawCaseByLawClientId(lawClientId));
        return founded;
    }

    @Operation(
            description = "To get LawCases with Lawyer by LawClient id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id with Lawyer and attached to the LawClient"),
            @ApiResponse(responseCode = "404", description = "LawClient was not found"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/toBringLawCase-withLawyer/" + "{lawClientId}")
    public LawClient findLawCaseWithLawyersByLawClientId(@PathVariable("lawClientId") String lawClientId){
        LawClient founded = lawClientService.getLawClientByID(lawClientId);
        founded.setLawCaseList(lawCaseClient.findLawCaseWithLawyerByLawClientId(lawClientId));
        return founded;
    }
}
