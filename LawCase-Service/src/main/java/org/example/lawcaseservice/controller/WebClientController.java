package org.example.lawcaseservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.example.lawcaseservice.client.LawClientClient;
import org.example.lawcaseservice.client.LawyerClient;
import org.example.lawcaseservice.domain.LawCase;
import org.example.lawcaseservice.domain.Lawyer;
import org.example.lawcaseservice.mapper.LawCaseMapper;
import org.example.lawcaseservice.service.LawCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawcase/webclient")
public class WebClientController {

    private final LawCaseService lawCaseService;
    private LawyerClient lawyerClient;
    private LawClientClient lawClientClient;
    private final LawCaseMapper lawCaseMapper;

    private static final Logger LOGGER
            = LoggerFactory.getLogger(WebClientController.class);

    // Between Lawyer-Service

    @Operation(
            description = "Send request to the Lawyer Service to Bring " +
                    "the Lawyer by Lawyer Id and attach it to the LawCases"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase delivered by Id and attached to the LawCases"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found by id"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })

    @GetMapping("/toBringLawyer/{lawyerId}") // dziala
    public ResponseEntity<List<LawCase>> bringLawyerByLawyerId(@PathVariable("lawyerId") String lawyerId) {
        List<LawCase> listOfFounded = lawCaseService
                .getAllLawCases()
                .stream()
                .filter(lawCase -> lawCase.getLawyerId()
                        .equals(lawyerId)).toList();
        Lawyer foundedLawyer = lawyerClient.requestLawyerByLawyerId(lawyerId);
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

    /*
    @GetMapping("/toBringLawyer/{lawyerId}")
    public ResponseEntity<List<LawCaseDTO>> bringLawyerByLawyerId(@PathVariable("lawyerId") String lawyerId) {

        List<LawCase> listOfFounded = lawCaseService
                .getAllLawCases()
                .stream()
                .filter(lawCase -> lawCase.getLawyerId()
                        .equals(lawyerId)).toList();
        Lawyer foundedLawyer = lawyerClient.sendLawyerByLawyerId(lawyerId);
        for (LawCase lawCase : listOfFounded) {
            lawCase.setLawyer(foundedLawyer);
            lawCaseService.updateLawCaseById( lawCase.getId(), lawCase);
        }
        List<LawCaseDTO> dtos = listOfFounded.stream()
                .map(lawCase -> modelMapper.map(lawCase, LawCaseDTO.class))
                .toList();
        System.out.println("4");
        return new ResponseEntity<>(dtos,HttpStatus.valueOf(200));
    }
    */



    /*




    @Operation(
            description = "Send LawCases with LawClients to the Lawyer microservice"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("forLawyer-withLawClient/{lawyerId}")
    public List<LawCase> findLawCaseWithLawClientsByLawyerIdAndSendThem(@PathVariable("lawyerId") String lawyerId){
        List<LawCase> lawCasesToSend = findLawCasesByLawyerIdAndSendThem(lawyerId);
        lawCasesToSend
                .forEach(lawCase ->
                        lawCase.setLawClient
                                (lawClientClient.findLawClientByLawClientId
                                        (lawCase.getLawClientId())));
        return lawCasesToSend;
    }

    // LawClient-Service
    @Operation(
            description = "Send LawCases to LawClients"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "404", description = "LawClient was not found by id"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/forLawClient/{lawClientId}")
    public List<LawCase> sendLawCasesByLawClientId(@PathVariable("lawClientId") String lawClientId){
        List<LawCase> lawCases
                = lawCaseService.getAllLawCases();
        return lawCases
                .stream()
                .filter(lawCase -> lawCase.getLawClientId().equals(lawClientId))
                .toList();
    }

    // To get from another services

    */


    /*
    @Operation(
            description = "Send LawCases to LawClients"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCLIENT delivered by lawCaseId and attached to the LawCase"),
            @ApiResponse(responseCode = "404", description = "LawCase was not found by id"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/toBringLawClient/{lawCaseId}")
    public LawCase bringLawClientForLawCase(@PathVariable("lawCaseId") String lawCaseId){
        List<LawCase> lawCases
                = lawCaseService.getAllLawCases();

        LawCase founded = lawCases.stream()
                .filter(lawCase -> lawCase.getId().equals(lawCaseId))
                .findFirst()
                .orElseThrow();

        founded.setLawClient(lawClientClient.findLawClientByLawClientId(founded.getLawClientId()));
        return founded;
    }

     */

    @GetMapping("/testLawyer")
    public String test1() {
        LOGGER.info("Taken from Lawyer Service");
        return lawyerClient.testToLawyerService();
    }

    @GetMapping("/testToLawyerService")
    public String test2() {
        LOGGER.info("Send to LawyerService");
        return "To Lawyer from Law Case";
    }

    @GetMapping("/testLawClient")
    public String test3() {
        LOGGER.info("Taken from Lawyer Service");
        return lawClientClient.testToLawClient();
    }

    @GetMapping("/testToLawClientService")
    public String test4() {
        LOGGER.info("Send to LawClientService");
        return "From LawCase to Law Client";
    }
}
