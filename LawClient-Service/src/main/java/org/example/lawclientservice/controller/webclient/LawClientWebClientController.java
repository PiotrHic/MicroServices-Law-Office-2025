package org.example.lawclientservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.lawclientservice.client.LawCaseClient;
import org.example.lawclientservice.domain.LawClient;
import org.example.lawclientservice.mapper.LawClientMapper;
import org.example.lawclientservice.service.LawClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lawclient/webclient")
public class WebClientController extends ParentController{

    LawCaseClient lawCaseClient;

    public WebClientController(LawClientService lawClientService, LawClientMapper lawClientMapper,
                               LawCaseClient lawCaseClient) {
        super(lawClientService, lawClientMapper);
        this.lawCaseClient = lawCaseClient;
    }


    @Operation(
            description = "Send LawClient to the LawCase microservice"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/sendLawClient" + NUMBER_QUERY_PATH) // dziala
    public LawClient sendLawClientByLawClientIdToLawCase(@PathVariable(NUMBER_VARIABLE_PATH) String lawClientId){
        LOGGER.info("WebClient request was send for the LawClient from LawCase with LawClient id: " + lawClientId);
        return lawClientService.getLawClientByID(lawClientId);
    }

    @Operation(
            description = "To get LawCases by LawClient id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the LawClient"),
            @ApiResponse(responseCode = "404", description = "LawClient was not found"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/getLawCases" + NUMBER_QUERY_PATH) // dziala
    public LawClient findLawCaseByLawClientId(@PathVariable(NUMBER_VARIABLE_PATH) String lawClientId){
        LawClient founded = lawClientService.getLawClientByID(lawClientId);
        founded.setLawCaseList(lawCaseClient.findLawCaseByLawClientId(lawClientId));
        lawClientService.updateLawClientById(lawClientId, founded);
        LOGGER.info("WebClient request was send for the LawClient with id: " + lawClientId);
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
    @GetMapping("/getLawCases-withLawyer" + NUMBER_QUERY_PATH)
    public LawClient findLawCaseWithLawyersByLawClientId(@PathVariable(NUMBER_VARIABLE_PATH) String lawClientId){
        LawClient founded = lawClientService.getLawClientByID(lawClientId);
        founded.setLawCaseList(lawCaseClient.findLawCaseWithLawyerByLawClientId(lawClientId));
        lawClientService.updateLawClientById(lawClientId, founded);
        LOGGER.info("WebClient request was send for the LawClient with Lawyer with id: " + lawClientId);
        return founded;
    }


}
