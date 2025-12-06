package org.example.lawclientservice.controller.webclient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.lawclientservice.domain.DTO.LawClientDTO;
import org.example.lawclientservice.webclient.LawCaseWebClient;
import org.example.lawclientservice.controller.ParentController;
import org.example.lawclientservice.domain.LawClient;
import org.example.lawclientservice.mapper.LawClientMapper;
import org.example.lawclientservice.service.LawClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/lawclient/webclient")
public class LawClientWebClientController extends ParentController {

    private static final String CB = "lawcase";

    LawCaseWebClient lawCaseWebClient;

    public LawClientWebClientController(LawClientService lawClientService, LawClientMapper lawClientMapper,
                                        LawCaseWebClient lawCaseWebClient) {
        super(lawClientService, lawClientMapper);
        this.lawCaseWebClient = lawCaseWebClient;
    }

    @Operation(
            description = "Send Request to the LawCase microservice to get LawCases with LawClient id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the LawClient"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @CircuitBreaker(name=CB, fallbackMethod = "testFallBack")
    @Retry(name=CB)
    @RateLimiter(name=CB)
    @GetMapping("/getLawCases" + NUMBER_QUERY_PATH) // dziala
    public ResponseEntity<LawClientDTO> findLawCaseByLawClientId(
            @PathVariable(NUMBER_VARIABLE_PATH) String lawClientId){
        LawClient founded = lawClientService.getLawClientByID(lawClientId);
        founded.setLawCaseList(lawCaseWebClient.findLawCaseByLawClientId(lawClientId));
        LawClient updated = lawClientService.updateLawClientById(lawClientId, founded);
        LawClientDTO dto = lawClientMapper.toDTO(updated);
        LOGGER.info("WebClient request was sent for the LawCases with Lawyer for the LawClient with id: {}"
                , lawClientId);
        return new ResponseEntity<>(dto, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Send Request to the LawCase microservice to get LawCases with LawClient id with Lawyer"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id with Lawyer and attached " +
                    "to the LawClient"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @CircuitBreaker(name=CB, fallbackMethod = "testFallBack")
    @Retry(name=CB)
    @RateLimiter(name=CB)
    @GetMapping("/getLawCases-withLawyer" + NUMBER_QUERY_PATH)
    public ResponseEntity<LawClientDTO> findLawCaseWithLawyersByLawClientId(
            @PathVariable(NUMBER_VARIABLE_PATH) String lawClientId){
        LawClient founded = lawClientService.getLawClientByID(lawClientId);
        founded.setLawCaseList(lawCaseWebClient.findLawCaseWithLawyerByLawClientId(lawClientId));
        LawClient updated = lawClientService.updateLawClientById(lawClientId, founded);
        LOGGER.info("WebClient request was sent for the LawCases for the LawClient with id: {}", lawClientId);
        return new ResponseEntity<>(lawClientMapper.toDTO(updated), HttpStatus.valueOf(200));
    }

    public ResponseEntity<LawClientDTO> testFallBack(String lawClientId, Throwable t) {
        LawClientDTO fallbackDTO = new LawClientDTO();
        fallbackDTO.setId(lawClientId);
        fallbackDTO.setLawCaseList(List.of());                                                                                                                                                                                                                             

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(fallbackDTO);
    }
}
