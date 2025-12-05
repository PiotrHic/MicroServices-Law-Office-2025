package org.example.lawyerservice.controller.webclient;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.lawyerservice.controller.ParentController;
import org.example.lawyerservice.domain.DTO.LawyerDTO;
import org.example.lawyerservice.mapper.LawCaseMapper;
import org.example.lawyerservice.mapper.LawyerMapper;
import org.example.lawyerservice.service.LawyerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lawyer/webclient")
public class LawCaseWebClientController extends ParentController {

    public LawCaseWebClientController(LawyerService lawyerService, LawyerMapper lawyerMapper,
                                      LawCaseMapper lawCaseMapper) {
        super(lawyerService, lawyerMapper, lawCaseMapper);
    }

    @Operation(
            description = "Send Lawyer by id to the LawCase microservice - /api/lawyer/webclient/sendLawyerToLawCase/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer delivered by id and attached to the LawCase"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_SHORT)
    })
    @GetMapping("/sendLawyerToLawCase" + NUMBER_QUERY_PATH)
    public ResponseEntity<LawyerDTO> sendLawyerByLawyerId(@PathVariable(NUMBER_VARIABLE_PATH) String lawyerId){
        LOGGER.info("Lawyer with id: {} was sent to the LawCase Service!", lawyerId);
        return new ResponseEntity<>(lawyerMapper.toDTO(lawyerService.getLawyerByID(lawyerId)),
                HttpStatus.valueOf(200));
    }

}
