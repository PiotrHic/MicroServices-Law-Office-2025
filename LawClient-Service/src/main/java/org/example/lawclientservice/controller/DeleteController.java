package org.example.lawclientservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.example.lawclientservice.domain.DTO.LawClientDTO;
import org.example.lawclientservice.domain.LawClient;
import org.example.lawclientservice.mapper.LawClientMapper;
import org.example.lawclientservice.service.LawClientService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawclient/delete")
public class DeleteController {

    private final LawClientService lawClientService;
    private final LawClientMapper lawClientMapper;
    private static final Logger LOGGER
            = LoggerFactory.getLogger(WebClientController.class);

    private final String NUMBER_VARIABLE_PATH = "lawClientId";
    private final String NUMBER_QUERY_PATH = "/{lawClientId}";


    @Operation(
            description = "Delete LawClient from the database by the id  - /api/lawclient/delete/byId/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "LawClient was not found by id"),
            @ApiResponse(responseCode = "500", description = "Parameter was not correct or Internal Server Error")
    })
    @DeleteMapping("/deleteById" + NUMBER_QUERY_PATH)
    ResponseEntity<LawClientDTO> deleteLawClientById(@PathVariable(NUMBER_VARIABLE_PATH) String lawClientId){
        LawClientDTO deleted = lawClientMapper.toDTO(lawClientService.getLawClientByID(lawClientId));
        LOGGER.info("LawClient deleted: {} by id from the database!", deleted.getName());
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @Operation(
            description = "Delete LawClient from the database by the name  " +
                    "- /api/lawclient/delete/byName?lawclientName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "LawClient was not found by name"),
            @ApiResponse(responseCode = "500", description = "Parameter was not correct or Internal Server Error")
    })
    @DeleteMapping("/byName") // ?lawyerName=
    ResponseEntity <LawClientDTO> deleteLawClientByName(@RequestParam String lawClientName){
        LawClient deleted = lawClientService.deleteLawClientByName(lawClientName);
        LawClientDTO updatedDTO = lawClientMapper.toDTO(lawClientService.getLawClientByID(lawClientName));
        LOGGER.info("LawClient: {} was deleted by name from the database!", lawClientName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Delete all LawClients from the database - /api/lawclient/delete/allLawClients"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All LawClients deleted"),
            @ApiResponse(responseCode = "500", description = "Parameter was not correct or Internal Server Error")
    })
    @DeleteMapping("/allLawClients")
    ResponseEntity <String> deleteAllLawClients(){
        lawClientService.deleteAllLawClients();
        LOGGER.info("Database is empty");
        return new ResponseEntity<>("Database is empty", HttpStatus.OK);
    }
}
