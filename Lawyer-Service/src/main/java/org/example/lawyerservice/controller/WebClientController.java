package org.example.lawyerservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.lawyerservice.client.LawCaseClient;
import org.example.lawyerservice.domain.DTO.LawyerDTO;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.mapper.LawyerMapper;
import org.example.lawyerservice.service.LawyerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lawyer/webclient")
public class WebClientController extends ParentController {

    private LawCaseClient lawCaseClient;

    public WebClientController(LawyerService lawyerService, LawyerMapper lawyerMapper, LawCaseClient lawCaseClient) {
        super(lawyerService, lawyerMapper);
        this.lawCaseClient = lawCaseClient;
    }

    @Operation(
            description = "Send Lawyer to the LawCase microservice - /api/lawyer/webclient/toSendLawyer/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not find by id"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/sendLawyerToLawCase/{lawyerId}") // dziala
    public Lawyer sendLawyerByLawyerId(@PathVariable("lawyerId") String lawyerId){
        LOGGER.info("Lawyer: {} was sent by id to the LawCase Service!", lawyerId);
        return lawyerService.getLawyerByID(lawyerId);
    }


    @Operation(
            description = "Send Request to the LawCase Service to bring the list " +
                    "of LawCases to the Lawyer by the Lawyer id - /api/lawyer/webclient/getLawCases/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the Lawyer"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/getLawCases/{lawyerId}") // dziala
    ResponseEntity<Lawyer> bringLawCaseByLawyerId(@PathVariable("lawyerId") String lawyerId){
        Lawyer founded = lawyerService.getLawyerByID(lawyerId);
        founded.setLawCaseList(lawCaseClient.findLawCasesByLawyerIdAndSendThem(lawyerId));
        lawyerService.updateLawyerById(lawyerId, founded);
        return new ResponseEntity<>(founded, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Get List of LawCases with LawCClients by Lawyer Id " +
                    "- /api/lawyer/webclient/getLawCases-withLawClient/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the Lawyer"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/getLawCases-withLawClient/{lawyerId}") // dziala
    public Lawyer bringLawCaseWithLawClientsByLawyerId(@PathVariable("lawyerId") String lawyerId){
        Lawyer founded = lawyerService.getLawyerByID(lawyerId);
        founded.setLawCaseList(lawCaseClient.bringLawCaseWithLawClientsByLawyerId(lawyerId));
        lawyerService.updateLawyerById(lawyerId, founded);
        return founded;
    }

}
