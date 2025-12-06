package org.example.lawcaseservice.controller.webclient.lawyer;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.lawcaseservice.controller.ParentController;
import org.example.lawcaseservice.domain.DTO.LawCaseDTO;
import org.example.lawcaseservice.domain.DTO.LawyerDTO;
import org.example.lawcaseservice.domain.LawCase;
import org.example.lawcaseservice.mapper.LawCaseMapper;
import org.example.lawcaseservice.mapper.LawyerMapper;
import org.example.lawcaseservice.service.LawCaseService;
import org.example.lawcaseservice.webclient.LawyerWebClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lawcase/webclient")
public class LawCaseWebClientController extends ParentController {

    private static final String CB = "lawcase";

    private final LawyerWebClient lawyerWebClient;

    public LawCaseWebClientController(LawCaseService lawCaseService, LawCaseMapper lawCaseMapper,
                                      LawyerMapper lawyerMapper, LawyerWebClient lawyerWebClient) {
        super(lawCaseService, lawCaseMapper, lawyerMapper);
        this.lawyerWebClient = lawyerWebClient;
    }

    @Operation(
            description = "Send request to the Lawyer Service to get " +
                    "Lawyer by Lawyer Id and attach it to the LawCases"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer delivered by Id and attached to the LawCases"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @CircuitBreaker(name=CB, fallbackMethod = "testFallBack")
    @Retry(name=CB)
    @RateLimiter(name=CB)
    @GetMapping("/getLawyer" + LAWYER_NUMBER_QUERY_PATH)
    public ResponseEntity<List<LawCaseDTO>> getLawyerByLawyerId(@PathVariable(LAWYER_NAME_VARIABLE_PATH)
                                                                    String lawyerId) {
        List<LawCase> listOfFounded = lawCaseService
                .getAllLawCases()
                .stream()
                .filter(lawCase -> lawCase.getLawyerId()
                        .equals(lawyerId)).toList();
        LawyerDTO foundedLawyer = lawyerWebClient.getLawyerByLawyerId(lawyerId).getBody();
        for (LawCase lawCase : listOfFounded) {
            lawCase.setLawyer(lawyerMapper.toEntity(foundedLawyer));
            lawCaseService.updateLawCaseById(lawCase.getId(), lawCase);
        }
        List<LawCaseDTO> dtos = listOfFounded
                .stream()
                .map(lawCaseMapper::toDTO)
                .toList();
        return new ResponseEntity<>(dtos, HttpStatus.valueOf(200));
    }

    public ResponseEntity<List<LawCaseDTO>> testFallBack(String lawyerId, Throwable t) {
        LawCaseDTO fallbackDTO = new LawCaseDTO();
        fallbackDTO.setLawyerId(lawyerId);

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(List.of(fallbackDTO));
    }

}
