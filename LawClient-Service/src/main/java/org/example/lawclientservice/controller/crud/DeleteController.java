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

@RestController
@RequestMapping("/api/lawclient/delete")
public class DeleteController extends ParentController {

    public DeleteController(LawClientService lawClientService, LawClientMapper lawClientMapper) {
        super(lawClientService, lawClientMapper);
    }

    @Operation(
            description = "Delete LawClient from the database by the id  - /api/lawclient/delete/byId/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient deleted successfully"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
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
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_NAME),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @DeleteMapping("/byName") // ?lawyerName=
    ResponseEntity <LawClientDTO> deleteLawClientByName(@RequestParam String lawClientName){
        LawClient deleted = lawClientService.deleteLawClientByName(lawClientName);
        LawClientDTO updatedDTO = lawClientMapper.toDTO(deleted);
        LOGGER.info("LawClient: {} was deleted by name from the database!", lawClientName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Delete all LawClients from the database - /api/lawclient/delete/allLawClients"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All LawClients deleted"),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @DeleteMapping("/allLawClients")
    ResponseEntity <String> deleteAllLawClients(){
        lawClientService.deleteAllLawClients();
        LOGGER.info("Database is empty");
        return new ResponseEntity<>("Database is empty", HttpStatus.OK);
    }
}
