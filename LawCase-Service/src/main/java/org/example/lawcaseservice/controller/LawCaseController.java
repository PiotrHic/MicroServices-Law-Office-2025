package org.example.lawcaseservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.lawcaseservice.client.LawClientClient;
import org.example.lawcaseservice.client.LawyerClient;
import org.example.lawcaseservice.domain.DTO.LawCaseDTO;
import org.example.lawcaseservice.domain.LawCase;
import org.example.lawcaseservice.service.LawCaseService;
import org.example.lawyerservice.exception.LawyerNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawcase")
@Tag(name = "LawCase Controller")
public class LawCaseController {

    private final LawCaseService lawCaseService;

    ModelMapper modelMapper;

    private LawyerClient lawyerClient;
    private LawClientClient lawClientClient;

    private static final Logger LOGGER
            = LoggerFactory.getLogger(LawCaseController.class);

    private final String NUMBER_VARIABLE_PATH = "lawCaseId";
    private final String NAME_VARIABLE_PATH = "lawCaseName";

    @Operation(
            description = "Creates a new LawCase and stores it in the database"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "LawCase created successfully"),
            @ApiResponse(responseCode = "500", description = "Invalid input data")
    })
    @PostMapping
    ResponseEntity<LawCaseDTO> createLawCase(@Valid @RequestBody LawCaseDTO lawCaseDTO){
        LawCase added = lawCaseService.createLawCase(modelMapper.map(lawCaseDTO, LawCase.class));
        LOGGER.info("LawCase: {} was added tp the database!", added.getName());
        return new ResponseEntity<>(modelMapper.map(added,LawCaseDTO.class),
                HttpStatus.valueOf(201));
    }

    @Operation(
            description = "Get LawCase from the database by the id  - api/lawcase/getById/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase delivered successfully"),
            @ApiResponse(responseCode = "404", description = "LawCase was not found")
    })
    @GetMapping("/getById/{lawCaseId}")
    ResponseEntity<LawCaseDTO> getLawCaseById(@PathVariable(NUMBER_VARIABLE_PATH) String lawCaseId) {
        LawCaseDTO foundedById = modelMapper.map(lawCaseService.getLawCaseById(lawCaseId),LawCaseDTO.class);
        LOGGER.info("LawCase: {} was founded by id in the database!", foundedById.getName());
        return new ResponseEntity<>(foundedById, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Get LawCase from the database by the name  - api/lawcase/getByName?lawcaseName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase delivered successfully"),
            @ApiResponse(responseCode = "404", description = "LawCase was not found")
    })
    @GetMapping("/getByName") // ?lawyerName=
    ResponseEntity<LawCaseDTO> getLawCaseByName(@RequestParam(NAME_VARIABLE_PATH) String lawCaseName) {
        LawCaseDTO foundedByName = modelMapper.map(lawCaseService.getLawCaseByName(lawCaseName),LawCaseDTO.class);
        LOGGER.info("LawCase: {} was founded by name in the database!", foundedByName.getName());
        return new ResponseEntity<>(foundedByName, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Get all LawCase from the Database"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase delivered"),
            @ApiResponse(responseCode = "500", description = "Empty Database")
    })
    @GetMapping("/getAllLawCases")
    ResponseEntity<List<LawCaseDTO>> getAllLawCases() {
        List<LawCase> lawCases = lawCaseService.getAllLawCases();
        if (lawCases.isEmpty()) {
            throw new LayerInstantiationException("There is no LawCases in the database!");
        }
        LOGGER.info("All LawCases were founded!");
        List<LawCaseDTO> lawCaseDTOs = lawCases.stream()
                .map(lawCase -> modelMapper.map(lawCase,LawCaseDTO.class))
                .toList();
        return new ResponseEntity<>(lawCaseDTOs, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Update LawCase from the database by the id  - /api/lawcase/updateById/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase delivered successfully"),
            @ApiResponse(responseCode = "404", description = "LawCase was not found by id"),
            @ApiResponse(responseCode = "500", description = "Parameter was not correct"),
    })
    @PutMapping("/updateById/{lawCaseId}")
    ResponseEntity<LawCaseDTO> updateLawCaseById(@PathVariable(NUMBER_VARIABLE_PATH) String lawCaseId,
                                               @Valid @RequestBody LawCase lawCase) {
        LawCase updated = lawCaseService.updateLawCaseById(lawCaseId, lawCase);
        LawCaseDTO updatedDTO = modelMapper.map(updated, LawCaseDTO.class);
        LOGGER.info("LawCase: {} was updated by id to the database!", lawCaseId);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Update LawCase from the database by the name  " +
                    "- /api/lawcase/updateByName?lawcaseName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase delivered successfully"),
            @ApiResponse(responseCode = "404", description = "LawCase was not found by name"),
            @ApiResponse(responseCode = "500", description = "Parameter was not correct"),
    })
    @PutMapping("/updateByName") // ?lawyerName=
    ResponseEntity<LawCaseDTO> updateLawCaseByName(@RequestParam String lawCaseName,@Valid @RequestBody LawCaseDTO lawCaseDTO){
        LawCase toUpdate = modelMapper.map(lawCaseDTO, LawCase.class);
        LawCase updated = lawCaseService.updateLawCaseByName(lawCaseName, toUpdate);
        LawCaseDTO updatedDTO = modelMapper.map(updated, LawCaseDTO.class);
        LOGGER.info("LawCase: {} was updated by name to the database!", lawCaseName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Delete LawCase from the database by the id  - /api/lawcase/deleteById/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase deleted successfully"),
            @ApiResponse(responseCode = "404", description = "LawCase was not found by id")
    })
    @DeleteMapping("/deleteById/{lawCaseId}")
    ResponseEntity <LawCaseDTO> deleteLawCaseById(@PathVariable(NUMBER_VARIABLE_PATH) String lawCaseId){
        LawCaseDTO deleted = modelMapper.map(lawCaseService.deleteLawCaseById(lawCaseId), LawCaseDTO.class);
        LOGGER.info("LawCase deleted: {} by id from the database!", deleted.getName());
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @Operation(
            description = "Delete LawCase from the database by the name  - deleteByName?lawcaseName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase deleted successfully"),
            @ApiResponse(responseCode = "404", description = "LawCase was not found by name")
    })
    @DeleteMapping("/deleteByName") // ?lawyerName=
    ResponseEntity <LawCaseDTO> deleteLawCaseByName(@RequestParam String lawCaseName){
        LawCase deleted= lawCaseService.deleteLawCaseByName(lawCaseName);
        LawCaseDTO updatedDTO = modelMapper.map(deleted, LawCaseDTO.class);
        LOGGER.info("LawCase: {} was deleted by name from the database!", lawCaseName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Delete all LawCases from the Database"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All LawCases deleted")
    })
    @DeleteMapping("/deleteAll")
    ResponseEntity <String> deleteAlLawCases(){
        lawCaseService.deleteAllLawCases();
        LOGGER.info("Database is empty");
        return new ResponseEntity<>("Database is empty", HttpStatus.OK);
    }

    // WebClient methods
    
    // Lawyer-Service

    @Operation(
            description = "Send LawCases to the Lawyer microservice"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the Lawyer"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("forLawyer/{lawyerId}")
    public List<LawCase> findLawCaseByLawyerId(@PathVariable("lawyerId") String lawyerId) {
        List<LawCase> lawCases
                = lawCaseService.getAllLawCases();
        List<LawCase> lawCasesToSend = lawCases
                .stream()
                .filter(lawCase -> lawCase.getLawyerId().equals(lawyerId))
                .toList();
        return lawCasesToSend;
    }

    @Operation(
            description = "Send LawCases with LawClients to the Lawyer microservice"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("forLawyer-withLawClient/{lawyerId}")
    public List<LawCase> findLawCaseWithLawClientsByLawyerId(@PathVariable("lawyerId") String lawyerId){
        List<LawCase> lawCasesToSend = findLawCaseByLawyerId(lawyerId);
        lawCasesToSend
                .forEach(lawCase ->
                        lawCase.setLawClient
                                (lawClientClient.findLawClientByLawClientId
                                        (lawCase.getLawClientId())));
        return lawCasesToSend;
    }

    // LawClient-Service
    @Operation(
            description = "Send LawCases to LawClients"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/forLawClient/{lawClientId}")
    public List<LawCase> findLawCaseByLawClientId(@PathVariable("lawClientId") String lawClientId){
        List<LawCase> lawCases
                = lawCaseService.getAllLawCases();
        return lawCases
                .stream()
                .filter(lawCase -> lawCase.getLawClientId().equals(lawClientId))
                .toList();
    }

    // To get from another services
    @Operation(
            description = "Send LawCases to LawClients"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/toBringLawyer/{lawyerId}")
    public LawCase findLawyerByLawyerId(@PathVariable("lawyerId") String lawyerId) {
        LawCase founded = lawCaseService
                .getAllLawCases()
                .stream()
                .filter(lawCase -> lawCase.getLawyerId()
                        .equals(lawyerId)).findFirst().orElseThrow();
        founded.setLawyer(lawyerClient.findLawyerByLawyerId(lawyerId));
        return founded;
    }

    @Operation(
            description = "Send LawCases to LawClients"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCase delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/toBringLawClient/{lawCaseId}")
    public LawCase findLawClientForLawCase(@PathVariable("lawCaseId") String lawCaseId){
        List<LawCase> lawCases
                = lawCaseService.getAllLawCases();

        LawCase founded = lawCases.stream()
                .filter(lawCase -> lawCase.getId().equals(lawCaseId))
                .findFirst()
                .orElseThrow();

        founded.setLawClient(lawClientClient.findLawClientByLawClientId(founded.getLawClientId()));
        return founded;
    }
}
