package org.example.lawcaseservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.lawcaseservice.client.LawClientClient;
import org.example.lawcaseservice.client.LawyerClient;
import org.example.lawcaseservice.domain.LawCase;
import org.example.lawcaseservice.domain.Lawyer;
import org.example.lawcaseservice.mapper.LawCaseMapper;
import org.example.lawcaseservice.service.LawCaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lawcase/webclient")
public class WebClientController extends ParentController {

    private LawyerClient lawyerClient;
    private LawClientClient lawClientClient;

    public WebClientController(LawCaseService lawCaseService, LawCaseMapper lawCaseMapper,
                               LawyerClient lawyerClient, LawClientClient lawClientClient) {
        super(lawCaseService, lawCaseMapper);
        this.lawyerClient = lawyerClient;
        this.lawClientClient = lawClientClient;
    }

    @Operation(
            description = "Send request to the Lawyer Service to Bring " +
                    "the Lawyer by Lawyer Id and attach it to the LawCases"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase delivered by Id and attached to the LawCases"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found by id"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })

    @GetMapping("/getLawyer/{lawyerId}") // dziala
    public ResponseEntity<List<LawCase>> bringLawyerByLawyerId(@PathVariable("lawyerId") String lawyerId) {
        List<LawCase> listOfFounded = lawCaseService
                .getAllLawCases()
                .stream()
                .filter(lawCase -> lawCase.getLawyerId()
                        .equals(lawyerId)).toList();
        Lawyer foundedLawyer = lawyerClient.getLawyerByLawyerId(lawyerId);
        for (LawCase lawCase : listOfFounded) {
            lawCase.setLawyer(foundedLawyer);
            lawCaseService.updateLawCaseById( lawCase.getId(), lawCase);
        }

        return new ResponseEntity<>(listOfFounded,HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Send LawCases to the Lawyer microservice"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the Lawyer"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/forLawyer/{lawyerId}") // dziala
    public List<LawCase> findLawCasesByLawyerIdAndSendThem(@PathVariable("lawyerId") String lawyerId) {
        List<LawCase> lawCases
                = lawCaseService.getAllLawCases();
        List<LawCase> lawCasesToSend = lawCases
                .stream()
                .filter(lawCase -> lawCase.getLawyerId().equals(lawyerId))
                .toList();
        LOGGER.info("List of Lawcases were send to the Lawyer with id: {} !", lawyerId
        );
        return lawCasesToSend;
    }


    @Operation(
            description = "Send LawCases with LawClients to the Lawyer microservice"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/forLawyer-WithLawClient/{lawyerId}")
    public List<LawCase> findLawCaseWithLawClientsByLawyerIdAndSendThem(@PathVariable("lawyerId") String lawyerId){
        List<LawCase> lawCasesToSend = findLawCasesByLawyerIdAndSendThem(lawyerId);
        lawCasesToSend
                .forEach(lawCase ->
                        lawCase.setLawClient
                                (lawClientClient.findLawClientByLawClientId
                                        (lawCase.getLawClientId())));
        return lawCasesToSend;
    }

    @Operation(
            description = "Request LawClient to LawCases from LawClient Service"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCllient delivered by lawCaseId and attached to the LawCase"),
            @ApiResponse(responseCode = "404", description = "LawCase was not found by id"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/getLawClient/{lawClientId}") // dziala
    public List<LawCase> bringLawClientForLawCase(@PathVariable("lawClientId") String lawClientId){

        List<LawCase> founded = lawCaseService.getAllLawCases()
                .stream()
                .filter(lawcase -> lawcase.getLawClientId().equals(lawClientId))
                .toList();
        for(LawCase lawCase : founded){
            lawCase.setLawClient(lawClientClient.findLawClientByLawClientId(lawClientId));
            lawCaseService.updateLawCaseById(lawCase.getId(),lawCase);
        }
        return founded;
    }

    @Operation(
            description = "Send LawCases to LawClients"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the LawClient"),
            @ApiResponse(responseCode = "404", description = "LawClient was not found by id"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/toLawClient/{lawClientId}") // dziala
    public List<LawCase> sendLawCasesByLawClientId(@PathVariable("lawClientId") String lawClientId){
        return lawCaseService.getAllLawCases()
                .stream()
                .filter(lawCase -> lawCase.getLawClientId().equals(lawClientId))
                .toList();
    }

    @Operation(
            description = "Send LawCases with Lawyer to LawClients"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases with Lawyer delivered by Id " +
                    "and attached to the LawClient"),
            @ApiResponse(responseCode = "404", description = "LawClient was not found by id"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/forLawClient-withLawyer/{lawClientId}")
    public List<LawCase> sendLawCasesWithLawyerToLawClient(@PathVariable("lawClientId") String lawClientId){
        List<LawCase> foundedByLawClientId = lawCaseService.getAllLawCases()
                .stream()
                .filter(lawCase -> lawCase.getLawClientId().equals(lawClientId))
                .toList();
        for(LawCase lawCase : foundedByLawClientId) {
            lawCase.setLawyer(lawyerClient.getLawyerByLawyerId(lawCase.getLawyerId()));
            lawCaseService.updateLawCaseById(lawCase.getId(), lawCase);
        }
        return foundedByLawClientId;
    }
}
