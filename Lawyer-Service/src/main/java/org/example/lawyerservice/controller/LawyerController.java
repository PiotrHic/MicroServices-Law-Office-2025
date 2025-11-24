package org.example.lawyerservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.lawyerservice.client.LawCaseClient;
import org.example.lawyerservice.domain.DTO.LawyerDTO;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.service.LawyerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawyer")
@Tag(name = "Lawyer Controller")
public class LawyerController {

    private final LawyerService lawyerService;
    private final LawCaseClient lawCaseClient;

    private final ModelMapper modelMapper;

    private static final Logger LOGGER
            = LoggerFactory.getLogger(LawyerController.class);

    private final String NUMBER_VARIABLE_PATH = "lawyerId";
    private final String NAME_VARIABLE_PATH = "lawyerName";


    @Operation(
            description = "Creates a new lawyer and stores it in the database"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Lawyer created successfully"),
            @ApiResponse(responseCode = "500", description = "Invalid input data")
    })
    @PostMapping
    ResponseEntity<LawyerDTO> createLawyer(@Valid @RequestBody LawyerDTO lawyerDTO){
        Lawyer added = lawyerService.addLawyer(modelMapper.map(lawyerDTO,Lawyer.class));
        LOGGER.info("Lawyer: {} was added tp the database!", added.getName());
        return new ResponseEntity<>(modelMapper.map(added,LawyerDTO.class),
                HttpStatus.valueOf(201));
    }

    @Operation(
            description = "Get Lawyer from the database by the id  - api/lawyer/getById/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer delivered successfully"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found by id")
    })
    @GetMapping("/getById/{lawyerId}")
    ResponseEntity<LawyerDTO> getLawyerById(@PathVariable(NUMBER_VARIABLE_PATH) String lawyerId) {
        LawyerDTO foundedById = modelMapper.map(lawyerService.getLawyerByID(lawyerId),LawyerDTO.class);
        LOGGER.info("Lawyer: {} was founded by id in the database!", foundedById.getName());
        return new ResponseEntity<>(foundedById, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Get Lawyer from the database by the name  - api/lawyer/getByName?lawyerName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer delivered successfully"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found")
    })
    @GetMapping("/getByName") // ?lawyerName=
    ResponseEntity<LawyerDTO> getLawyerByName(@RequestParam(NAME_VARIABLE_PATH) String lawyerName) {
        LawyerDTO foundedByName = modelMapper.map(lawyerService.getLawyerByName(lawyerName),LawyerDTO.class);
        LOGGER.info("Lawyer: {} was founded by name in the database!", foundedByName.getName());
        return new ResponseEntity<>(foundedByName, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Get all Lawyers from the Database"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyers delivered"),
            @ApiResponse(responseCode = "500", description = "Empty Database")
    })
    @GetMapping("/getAllLawyers")
    ResponseEntity<List<LawyerDTO>> getAllLawyers() {
        List<Lawyer> lawyers = lawyerService.getAllLawyers();
        if (lawyers.isEmpty()) {
            throw new LayerInstantiationException("There is no lawyers in the database!");
        }
        LOGGER.info("All Lawyers were founded!");
        List<LawyerDTO> lawyersDTO = lawyers.stream()
                .map(lawyer -> modelMapper.map(lawyer,LawyerDTO.class))
                .toList();
        return new ResponseEntity<>(lawyersDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Update Lawyer from the database by the id  - /api/lawyer/updateById/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer delivered successfully"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found by id"),
            @ApiResponse(responseCode = "500", description = "Parameter was not correct"),
    })
    @PutMapping("/updateById/{lawyerId}")
    ResponseEntity<LawyerDTO> updateLawyerById(@PathVariable(NUMBER_VARIABLE_PATH) String lawyerId,
                                               @Valid @RequestBody LawyerDTO lawyerDTO) {
        Lawyer toUpdate = modelMapper.map(lawyerDTO, Lawyer.class);
        Lawyer updated = lawyerService.updateLawyerById(lawyerId, toUpdate);
        LawyerDTO updatedDTO = modelMapper.map(updated, LawyerDTO.class);
        LOGGER.info("Lawyer: {} was updated by id to the database!", lawyerId);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Update Lawyer from the database by the name  - /api/lawyer/updateByName?lawyerName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer delivered successfully"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found by name"),
            @ApiResponse(responseCode = "500", description = "Parameter was not correct"),
    })
    @PutMapping("/updateByName") // ?lawyerName=
    ResponseEntity<LawyerDTO> updateLawyerByName(@RequestParam String lawyerName,@Valid @RequestBody LawyerDTO lawyerDTO){
        Lawyer toUpdate = modelMapper.map(lawyerDTO, Lawyer.class);
        Lawyer updated = lawyerService.updateLawyerByName(lawyerName, toUpdate);
        LawyerDTO updatedDTO = modelMapper.map(updated, LawyerDTO.class);
        LOGGER.info("Lawyer: {} was updated by name to the database!", lawyerName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Delete Lawyer from the database by the id  - /api/lawyer/deleteById/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found by id")
    })
    @DeleteMapping("/deleteById/{lawyerId}")
    ResponseEntity <LawyerDTO> deleteLawyerById(@PathVariable(NUMBER_VARIABLE_PATH) String lawyerId){
        LawyerDTO deleted = modelMapper.map(lawyerService.deleteLawyerById(lawyerId), LawyerDTO.class);
        LOGGER.info("Lawyer deleted: {} by id from the database!", deleted.getName());
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @Operation(
            description = "Delete Lawyer from the database by the name  - deleteByName?lawyerName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found by name")
    })
    @DeleteMapping("/deleteByName") // ?lawyerName=
    ResponseEntity <LawyerDTO> deleteLawyerByName(@RequestParam String lawyerName){
        Lawyer deleted= lawyerService.deleteLawyerByName(lawyerName);
        LawyerDTO updatedDTO = modelMapper.map(deleted, LawyerDTO.class);
        LOGGER.info("Lawyer: {} was deleted from the database!", lawyerName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Delete all Lawyers from the Database"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All Lawyers deleted")
    })
    @DeleteMapping("/deleteAll")
    ResponseEntity <String> deleteAlLawyers(){
        lawyerService.deleteAlLawyers();
        LOGGER.info("Database is empty");
        return new ResponseEntity<>("Database is empty", HttpStatus.OK);
    }

    // WebClient methods

    // LawCase

    @Operation(
            description = "Send Lawyer to the LawCase microservice"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/toBringLawyer/{lawyerId}")
    public Lawyer findLawyerByLawyerId(@PathVariable("lawyerId") String lawyerId){
        return lawyerService.getLawyerByID(lawyerId);
    }

    @Operation(
            description = "Get List of LawCases by Lawyer Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/forLawCases/{lawyerId}")
    public Lawyer findLawCaseByLawyerId(@PathVariable("lawyerId") String lawyerId){
        Lawyer founded = lawyerService.getLawyerByID(lawyerId);
        founded.setLawCaseList(lawCaseClient.findLawCaseByLawyerId(lawyerId));
        return founded;
    }

    @Operation(
            description = "Get List of LawCases by Lawyer Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "LawCases delivered by Id and attached to the LawCase"),
            @ApiResponse(responseCode = "404", description = "Lawyer was not found"),
            @ApiResponse(responseCode = "500", description = "Some internal server error")
    })
    @GetMapping("/forLawCases-withLawClient/{lawyerId}")
    public Lawyer findLawCaseWithLawClientsByLawyerId(@PathVariable("lawyerId") String lawyerId){
        Lawyer founded = lawyerService.getLawyerByID(lawyerId);
        founded.setLawCaseList(lawCaseClient.findLawCaseWithLawClientsByLawyerId(lawyerId));
        return founded;
    }

}
