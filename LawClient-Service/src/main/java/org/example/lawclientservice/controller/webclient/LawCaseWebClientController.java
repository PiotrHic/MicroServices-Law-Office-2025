package org.example.lawclientservice.controller.webclient;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lawclient/webclient")
public class LawCaseWebClientController extends ParentController {

    public LawCaseWebClientController(LawClientService lawClientService, LawClientMapper lawClientMapper) {
        super(lawClientService, lawClientMapper);
    }

    @Operation(
            description = "Send LawClient to the LawCase microservice by LawClient id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @GetMapping("/sendLawClient" + NUMBER_QUERY_PATH)
    public ResponseEntity<LawClientDTO> sendLawClientByLawClientIdToLawCase(@PathVariable(NUMBER_VARIABLE_PATH)
                                                                                String lawClientId){
        LOGGER.info("LawClient with id: {} was sent to LawCase Service", lawClientId);
        LawClientDTO dto = lawClientMapper.toDTO(lawClientService.getLawClientByID(lawClientId));
        return new ResponseEntity<>(dto, HttpStatus.valueOf(200));
    }

}
