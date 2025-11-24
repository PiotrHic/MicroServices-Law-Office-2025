package org.example.lawyerservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.example.lawyerservice.client.LawCaseClient;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.service.LawyerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawyer")
public class LawyerController {

    private final LawyerService lawyerService;
    private final LawCaseClient lawCaseClient;

    private static final Logger LOGGER
            = LoggerFactory.getLogger(LawyerController.class);

    // WebClient methods

    // LawCase

    @Operation(
            description = "Send Lawyer to the LawCase microservice"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/toBringLawyer/{lawyerId}")
    public Lawyer findLawyerByLawyerId(@PathVariable("lawyerId") String lawyerId){
        return lawyerService.getLawyerByID(lawyerId);
    }

    @Operation(
            description = "Get List of LawCases by Lawyer Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/forLawCases/{lawyerId}")
    public Lawyer findLawCaseByLawyerId(@PathVariable("lawyerId") String lawyerId){
        Lawyer founded = lawyerService.getLawyerByID(lawyerId);
        founded.setLawCaseList(lawCaseClient.findLawCaseByLawyerId(lawyerId));
        return founded;
    }

    @Operation(
            description = "Get List of LawCases by Lawyer Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/forLawCases-withLawClient/{lawyerId}")
    public Lawyer findLawCaseWithLawClientsByLawyerId(@PathVariable("lawyerId") String lawyerId){
        Lawyer founded = lawyerService.getLawyerByID(lawyerId);
        founded.setLawCaseList(lawCaseClient.findLawCaseWithLawClientsByLawyerId(lawyerId));
        return founded;
    }

}
