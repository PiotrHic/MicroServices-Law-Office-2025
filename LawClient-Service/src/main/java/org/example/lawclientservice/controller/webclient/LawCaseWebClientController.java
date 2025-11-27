package org.example.lawclientservice.controller.webclient;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lawclient/webclient")
public class LawCaseWebClientController extends ParentController {

    public LawCaseWebClientController(LawClientService lawClientService, LawClientMapper lawClientMapper) {
        super(lawClientService, lawClientMapper);
    }

    @Operation(
            description = "Send LawClient to the LawCase microservice by LawClient id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawClient delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @GetMapping("/sendLawClient" + NUMBER_QUERY_PATH)
    public ResponseEntity<LawClientDTO> sendLawClientByLawClientIdToLawCase(@PathVariable(NUMBER_VARIABLE_PATH)
                                                                                String lawClientId){
        LOGGER.info("LawClient with id: {} was sent to LawCase Service", lawClientId);
        LawClientDTO dto = lawClientMapper.toDTO(lawClientService.getLawClientByID(lawClientId));
        return new ResponseEntity<>(dto, HttpStatus.valueOf(200));
    }

}
