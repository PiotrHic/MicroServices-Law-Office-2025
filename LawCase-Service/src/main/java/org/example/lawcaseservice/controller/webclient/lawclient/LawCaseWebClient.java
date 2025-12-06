package org.example.lawcaseservice.controller.webclient.lawclient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.lawcaseservice.controller.ParentController;
import org.example.lawcaseservice.domain.DTO.LawCaseDTO;
import org.example.lawcaseservice.domain.LawCase;
import org.example.lawcaseservice.domain.LawClient;
import org.example.lawcaseservice.mapper.LawCaseMapper;
import org.example.lawcaseservice.mapper.LawyerMapper;
import org.example.lawcaseservice.service.LawCaseService;
import org.example.lawcaseservice.webclient.LawClientWebClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lawcase/webclient")
public class LawCaseWebClient extends ParentController {

    private static final String CB = "lawcase";

    private LawClientWebClient lawClientWebClient;

    public LawCaseWebClient(LawCaseService lawCaseService, LawCaseMapper lawCaseMapper,
                            LawyerMapper lawyerMapper, LawClientWebClient lawClientWebClient) {
        super(lawCaseService, lawCaseMapper, lawyerMapper);
        this.lawClientWebClient = lawClientWebClient;
    }

    @Operation(
            description = "Request LawClient to LawCases from LawClient Service"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @CircuitBreaker(name=CB, fallbackMethod = "testFallBack")
    @Retry(name=CB)
    @RateLimiter(name=CB)
    @GetMapping("/getLawClient" + LAWCLIENT_NUMBER_QUERY_PATH)
    public ResponseEntity<List<LawCaseDTO>> bringLawClientForLawCase(
            @PathVariable(LAWCLIENT_NAME_VARIABLE_PATH) String lawClientId){

        List<LawCase> founded = lawCaseService.getAllLawCases()
                .stream()
                .filter(lawcase -> lawcase.getLawClientId().equals(lawClientId))
                .toList();
        LawClient forCircuitBreaker = lawClientWebClient.findLawClientByLawClientId(lawClientId);
        for(LawCase lawCase : founded){
            lawCase.setLawClient(lawClientWebClient.findLawClientByLawClientId(lawClientId));
            lawCaseService.updateLawCaseById(lawCase.getId(),lawCase);
        }
        List<LawCaseDTO> dtos = founded
                .stream()
                .map(lawCaseMapper::toDTO)
                .toList();
        return new ResponseEntity<>(dtos, HttpStatus.valueOf(200));
    }

    public ResponseEntity<List<LawCaseDTO>> testFallBack(String lawClientId, Throwable t) {
        LawCaseDTO fallbackDTO = new LawCaseDTO();
        fallbackDTO.setLawClientId(lawClientId);

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(List.of(fallbackDTO));
    }
}
