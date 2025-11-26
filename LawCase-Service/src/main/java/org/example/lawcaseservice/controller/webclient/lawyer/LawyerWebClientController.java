package org.example.lawcaseservice.controller.webclient.lawyer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.lawcaseservice.domain.DTO.LawCaseDTO;
import org.example.lawcaseservice.mapper.LawyerMapper;
import org.example.lawcaseservice.webclient.LawClientWebClient;
import org.example.lawcaseservice.controller.ParentController;
import org.example.lawcaseservice.domain.LawCase;
import org.example.lawcaseservice.mapper.LawCaseMapper;
import org.example.lawcaseservice.service.LawCaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lawcase/webclient")
public class LawyerWebClientController extends ParentController {

    private final LawClientWebClient lawClientWebClient;

    public LawyerWebClientController(LawCaseService lawCaseService, LawCaseMapper lawCaseMapper,
                                     LawyerMapper lawyerMapper, LawClientWebClient lawClientWebClient) {
        super(lawCaseService, lawCaseMapper, lawyerMapper);
        this.lawClientWebClient = lawClientWebClient;
    }

    @Operation(
            description = "Send LawCases to the Lawyer microservice by Lawyer Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the Lawyer"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @GetMapping("/sendLawCases" + LAWYER_NUMBER_QUERY_PATH) // dziala
    public ResponseEntity<List<LawCaseDTO>> findLawCasesByLawyerIdAndSendThem(
            @PathVariable(LAWYER_NAME_VARIABLE_PATH) String lawyerId) {
        List<LawCase> lawCasesToSend = lawCaseService.getAllLawCases()
                .stream()
                .filter(lawCase -> lawCase.getLawyerId().equals(lawyerId))
                .toList();
        LOGGER.info("List of LawCases were send to the Lawyer with id: {} !", lawyerId);
        List <LawCaseDTO> dtos = lawCasesToSend
                .stream()
                .map(lawCaseMapper::toDTO)
                .toList();
        return new ResponseEntity<>(dtos, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Send LawCases with LawClients to the Lawyer microservice by Lawyer Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @GetMapping("/sendLawCases-WithLawClients" + LAWYER_NUMBER_QUERY_PATH)
    public ResponseEntity<List<LawCaseDTO>> findLawCaseWithLawClientsByLawyerIdAndSendThem
            (@PathVariable(LAWYER_NAME_VARIABLE_PATH) String lawyerId){
        List<LawCaseDTO> lawCasesToSend = findLawCasesByLawyerIdAndSendThem(lawyerId).getBody();
        assert lawCasesToSend != null;
        lawCasesToSend
                .forEach(lawCase ->
                        lawCase.setLawClient
                                (lawClientWebClient.findLawClientByLawClientId
                                        (lawCase.getLawClientId())));
        return new ResponseEntity<>(lawCasesToSend, HttpStatus.valueOf(200));
    }
}
