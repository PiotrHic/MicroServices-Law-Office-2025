package org.example.lawclientservice.controller;

import org.example.lawclientservice.client.LawCaseClient;
import org.example.lawclientservice.mapper.LawClientMapper;
import org.example.lawclientservice.service.LawClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lawclient/webclient")
public class WebClientController extends ParentController{

    private LawCaseClient lawCaseClient;

    public WebClientController(LawClientService lawClientService, LawClientMapper lawClientMapper) {
        super(lawClientService, lawClientMapper);
    }

    /*
    @Operation(
            description = "Send LawClient to the LawCase microservice"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/sendToLawCase" + NUMBER_QUERY_PATH)
    public LawClient sendLawClientByLawClientIdToLawCase(@PathVariable(LAWCLIENT_ID) String lawClientId){
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
    @GetMapping("/toBringLawCase" + NUMBER_QUERY_PATH)
    public LawClient findLawCaseByLawClientId(@PathVariable(LAWCLIENT_ID) String lawClientId){
        LawClient founded = lawClientService.getLawClientByID(lawClientId);
        founded.setLawCaseList(lawCaseClient.findLawCaseByLawClientId(lawClientId));
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
    @GetMapping("/toBringLawCase-withLawyer" + NUMBER_QUERY_PATH)
    public LawClient findLawCaseWithLawyersByLawClientId(@PathVariable(LAWCLIENT_ID) String lawClientId){
        LawClient founded = lawClientService.getLawClientByID(lawClientId);
        founded.setLawCaseList(lawCaseClient.findLawCaseWithLawyerByLawClientId(lawClientId));
        LOGGER.info("WebClient request was send for the LawClient with Lawyer with id: " + lawClientId);
        return founded;
    }
    */

    @GetMapping("/testFromLawCaseService")
    public String testLawCase(){
        LOGGER.info("Taken from LawCase Service");
        return lawCaseClient.testToLawCaseService();
    }

    @GetMapping("/testToLawCaseService")
    public String testSend1(){
        LOGGER.info("Send to LawCase Service");
        return "From LawClient-Service to LawCase-Service";
    }

}
