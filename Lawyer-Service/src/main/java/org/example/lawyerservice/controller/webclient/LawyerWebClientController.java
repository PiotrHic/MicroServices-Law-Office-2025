package org.example.lawyerservice.controller.webclient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.lawyerservice.webclient.LawCaseWebClient;
import org.example.lawyerservice.controller.ParentController;
import org.example.lawyerservice.domain.DTO.LawyerDTO;
import org.example.lawyerservice.domain.LawCase;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.mapper.LawCaseMapper;
import org.example.lawyerservice.mapper.LawyerMapper;
import org.example.lawyerservice.service.LawyerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lawyer/webclient")
public class LawyerWebClientController extends ParentController {

    private static final String CB = "lawyer";

    private final LawCaseWebClient lawCaseWebClient;

    public LawyerWebClientController(LawyerService lawyerService, LawyerMapper lawyerMapper,
                                     LawCaseMapper lawCaseMapper, LawCaseWebClient lawCaseWebClient) {
        super(lawyerService, lawyerMapper, lawCaseMapper);
        this.lawCaseWebClient = lawCaseWebClient;
    }

    @Operation(
            description = "Send Request to the LawCase Service to bring the list " +
                    "of LawCases to the Lawyer by the Lawyer id - /api/lawyer/webclient/getLawCases/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Lawyer Id " +
                    "and attached to the Lawyer"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_SHORT)
    })
    @CircuitBreaker(name=CB, fallbackMethod = "testFallBack")
    @Retry(name=CB)
    @RateLimiter(name=CB)
    @GetMapping("/getLawCases" + NUMBER_QUERY_PATH)
    ResponseEntity<LawyerDTO> getLawCaseByLawyerId(@PathVariable(NUMBER_VARIABLE_PATH) String lawyerId){
        Lawyer founded = lawyerService.getLawyerByID(lawyerId);
        List<LawCase> lawCases = lawCaseWebClient.getLawCasesByLawyerId(lawyerId)
                .stream()
                .map(lawCaseMapper::toEntity)
                .toList();
        founded.setLawCaseList(lawCases);
        lawyerService.updateLawyerById(lawyerId, founded);
        LOGGER.info("LawCases were attached to Lawyer by the lawyerID : {}!", lawyerId);
        return new ResponseEntity<>(lawyerMapper.toDTO(founded), HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Send Request to the LawCase Service to bring the list " +
                    "of LawCases with LawClients to the Lawyer by the Lawyer id - /api/lawyer/webclient/getLawCases/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases with LawClients delivered by Lawyer Id " +
                    "and attached to the Lawyer"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_SHORT)
    })

    @CircuitBreaker(name=CB, fallbackMethod = "testFallBack")
    @Retry(name=CB)
    @RateLimiter(name=CB)
    @GetMapping("/getLawCases-withLawClient" + NUMBER_QUERY_PATH)
    public ResponseEntity<LawyerDTO> getLawCasesWithLawClientsByLawyerId(@PathVariable(NUMBER_VARIABLE_PATH)
                                                                             String lawyerId){
        Lawyer founded = lawyerService.getLawyerByID(lawyerId);
        List<LawCase> lawCases = lawCaseWebClient.getLawCasesWithLawClientsByLawyerId(lawyerId)
                .stream()
                .map(lawCaseMapper::toEntity)
                .toList();
        founded.setLawCaseList(lawCases);
        lawyerService.updateLawyerById(lawyerId, founded);
        LOGGER.info("LawCases with LawClients were requested by the lawyerID : {}!", lawyerId);
        return new ResponseEntity<>(lawyerMapper.toDTO(founded), HttpStatus.valueOf(200));
    }

    public ResponseEntity<LawyerDTO> testFallBack(String lawyerId, Throwable t) {
        LawyerDTO fallbackDTO = new LawyerDTO();
        fallbackDTO.setId(lawyerId);
        fallbackDTO.setLawCaseList(List.of()); // empty fallback list

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(fallbackDTO);
    }
}
