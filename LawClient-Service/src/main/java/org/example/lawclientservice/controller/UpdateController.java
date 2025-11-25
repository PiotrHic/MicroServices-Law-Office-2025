package org.example.lawclientservice.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.example.lawclientservice.domain.DTO.LawClientDTO;
import org.example.lawclientservice.domain.LawClient;
import org.example.lawclientservice.mapper.LawClientMapper;
import org.example.lawclientservice.service.LawClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lawclient/update")
public class UpdateController extends ParentController{

    public UpdateController(LawClientService lawClientService, LawClientMapper lawClientMapper) {
        super(lawClientService, lawClientMapper);
    }

    @Operation(
            description = "Update LawClient from the database by the id  - /api/lawclient/update/byId/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient delivered successfully"),
            @ApiResponse(responseCode = "404", description = "LawClient was not found by id"),
            @ApiResponse(responseCode = "500", description = "Parameter was not correct or Internal Server Error")
    })
    @PutMapping("/byId" + NUMBER_QUERY_PATH)
    ResponseEntity<LawClientDTO> updateLawClientById(@PathVariable(NUMBER_VARIABLE_PATH) String lawClientId,
                                                     @Valid @RequestBody LawClientDTO lawClientDTO) {
        LawClient toUpdate = lawClientMapper.toEntity(lawClientDTO);
        LawClient updated = lawClientService.updateLawClientById(lawClientId, toUpdate);
        LawClientDTO updatedDTO = lawClientMapper.toDTO(updated);
        LOGGER.info("LawClient: {} was updated by id to the database!", lawClientId);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Update LawClient from the database by the name  " +
                    "- /api/lawclient/update/byName?lawclientName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient delivered successfully"),
            @ApiResponse(responseCode = "404", description = "LawClient was not found by name"),
            @ApiResponse(responseCode = "500", description = "Parameter was not correct or Internal Server Error")
    })
    @PutMapping("/byName") // ?lawyerName=
    ResponseEntity<LawClientDTO> updateLawClientByName(@RequestParam String lawClientName,@Valid
    @RequestBody LawClientDTO lawClientDTO){
        LawClient toUpdate = lawClientMapper.toEntity(lawClientDTO);
        LawClient updated = lawClientService.updateLawClientByName(lawClientName, toUpdate);
        LawClientDTO updatedDTO = lawClientMapper.toDTO(updated);
        LOGGER.info("LawClient: {} was updated by name to the database!", lawClientName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }
}
