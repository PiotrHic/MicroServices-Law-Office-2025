package org.example.lawcaseservice.controller.webclient.lawclient;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.lawcaseservice.domain.DTO.LawCaseDTO;
import org.example.lawcaseservice.domain.Lawyer;
import org.example.lawcaseservice.mapper.LawyerMapper;
import org.example.lawcaseservice.webclient.LawyerWebClient;
import org.example.lawcaseservice.controller.ParentController;
import org.example.lawcaseservice.domain.LawCase;
import org.example.lawcaseservice.mapper.LawCaseMapper;
import org.example.lawcaseservice.service.LawCaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lawcase/webclient")
public class LawClientWebClientController extends ParentController {

    private LawyerWebClient lawyerWebClient;

    public LawClientWebClientController(LawCaseService lawCaseService, LawCaseMapper lawCaseMapper,
                                        LawyerMapper lawyerMapper, LawyerWebClient lawyerWebClient) {
        super(lawCaseService, lawCaseMapper, lawyerMapper);
        this.lawyerWebClient = lawyerWebClient;
    }

    @Operation(
            description = "Send LawCases to LawClient by LawClient Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the LawClient"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @GetMapping("/toLawClient/{lawClientId}") // dziala
    public ResponseEntity<List<LawCaseDTO>> sendLawCasesByLawClientId(
            @PathVariable(LAWCLIENT_NAME_VARIABLE_PATH) String lawClientId){
        List <LawCase> lawCases = lawCaseService.getAllLawCases()
                .stream()
                .filter(lawCase -> lawCase.getLawClientId().equals(lawClientId))
                .toList();
        List <LawCaseDTO> dtos = lawCases.stream().map(lawCaseMapper::toDTO).toList();
        return new ResponseEntity<>(dtos, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Send LawCases with Lawyer to LawClient by lawClient Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases with Lawyer delivered by Id " +
                    "and attached to the LawClient"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @GetMapping("/toLawClient-withLawyer/{lawClientId}")
    public ResponseEntity<List<LawCaseDTO>> sendLawCasesWithLawyerToLawClient(
            @PathVariable(LAWCLIENT_NAME_VARIABLE_PATH) String lawClientId){
        List<LawCase> founded = lawCaseService.getAllLawCases()
                .stream()
                .filter(lawCase -> lawCase.getLawClientId().equals(lawClientId))
                .toList();
        for(LawCase lawCase : founded) {
            Lawyer lawyer = lawyerMapper.toEntity(
                    lawyerWebClient.getLawyerByLawyerId(lawCase.getLawyerId()).getBody());
            lawCase.setLawyer(lawyer);
            lawCaseService.updateLawCaseById(lawCase.getId(), lawCase);
        }
        List <LawCaseDTO> dtos = founded.stream().map(lawCaseMapper::toDTO).toList();
        return new ResponseEntity<>(dtos, HttpStatus.valueOf(200));
    }
}
