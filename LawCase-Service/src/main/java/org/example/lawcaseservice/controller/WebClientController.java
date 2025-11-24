package org.example.lawcaseservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.lawcaseservice.client.LawClientClient;
import org.example.lawcaseservice.client.LawyerClient;
import org.example.lawcaseservice.domain.DTO.LawCaseDTO;
import org.example.lawcaseservice.domain.LawCase;
import org.example.lawcaseservice.service.LawCaseService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawcase/webclient")
@Tag(name = "LawCase Controller")
public class WebClientController {

    private final LawCaseService lawCaseService;
    private LawyerClient lawyerClient;
    private LawClientClient lawClientClient;

    private static final Logger LOGGER
            = LoggerFactory.getLogger(WebClientController.class);

    // Lawyer-Service

    @Operation(
            description = "Send LawCases to the Lawyer microservice"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the Lawyer"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("forLawyer/{lawyerId}")
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
    @Operation(
            description = "Send LawCases to LawClients"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found by id"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/toBringLawyer/{lawyerId}")
    public LawCase bringLawyerByLawyerId(@PathVariable("lawyerId") String lawyerId) {
        LawCase founded = lawCaseService
                .getAllLawCases()
                .stream()
                .filter(lawCase -> lawCase.getLawyerId()
                        .equals(lawyerId)).findFirst().orElseThrow();
        founded.setLawyer(lawyerClient.findLawyerByLawyerId(lawyerId));
        return founded;
    }

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
}
