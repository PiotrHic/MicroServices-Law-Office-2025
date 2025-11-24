package org.example.lawclientservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawclient")
@Tag(name = "LawClient Controller")
public class WebClientController {

    private final LawClientService lawClientService;

    ModelMapper modelMapper;

    private static final Logger LOGGER
            = LoggerFactory.getLogger(WebClientController.class);

    private final String NUMBER_VARIABLE_PATH = "lawClientId";
    private final String NUMBER_QUERY_PATH = "/{lawClientId}";
    private final String NAME_VARIABLE_PATH = "lawClientName";

    private final LawCaseClient lawCaseClient;

    @Operation(
            description = "Send LawClient to the LawCase microservice"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/sendToLawCase" + NUMBER_QUERY_PATH)
    public LawClient sendLawClientByLawClientIdToLawCase(@PathVariable("lawClientId") String lawClientId){
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
    @GetMapping("/toBringLawCase-withLawyer" + NUMBER_QUERY_PATH)
    public LawClient findLawCaseWithLawyersByLawClientId(@PathVariable("lawClientId") String lawClientId){
        LawClient founded = lawClientService.getLawClientByID(lawClientId);
        founded.setLawCaseList(lawCaseClient.findLawCaseWithLawyerByLawClientId(lawClientId));
        return founded;
    }
}
