package org.example.lawcaseservice.controller.webclient.lawyer;

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

    @GetMapping("/getLawyer/{lawyerId}")
    public ResponseEntity<List<LawCaseDTO>> getLawyerByLawyerId(@PathVariable("lawyerId") String lawyerId) {
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

}
