package org.example.lawyerservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.example.lawyerservice.client.LawCaseClient;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.service.LawyerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawyer")
public class WebClientController {

    private final LawyerService lawyerService;
    private final LawCaseClient lawCaseClient;

    private static final Logger LOGGER
            = LoggerFactory.getLogger(WebClientController.class);

    @Operation(
            description = "Send Lawyer to the LawCase microservice"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not find by id"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/toSendLawyer/{lawyerId}")
    public Lawyer sendLawyerByLawyerId(@PathVariable("lawyerId") String lawyerId){
        LOGGER.info("Lawyer: {} was sent by id to the LawCase Service!", lawyerId);
        return lawyerService.getLawyerByID(lawyerId);
    }

    @Operation(
            description = "Get List of LawCases by Lawyer Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the Lawyer"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/getLawCases/{lawyerId}")
    public Lawyer bringLawCaseByLawyerId(@PathVariable("lawyerId") String lawyerId){
        Lawyer founded = lawyerService.getLawyerByID(lawyerId);
        founded.setLawCaseList(lawCaseClient.bringLawCaseByLawyerId(lawyerId));
        return founded;
    }

    @Operation(
            description = "Get List of LawCases by Lawyer Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the Lawyer"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/getLawCases-withLawClient/{lawyerId}")
    public Lawyer bringLawCaseWithLawClientsByLawyerId(@PathVariable("lawyerId") String lawyerId){
        Lawyer founded = lawyerService.getLawyerByID(lawyerId);
        founded.setLawCaseList(lawCaseClient.bringLawCaseWithLawClientsByLawyerId(lawyerId));
        return founded;
    }

}
