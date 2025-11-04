package org.example.lawyerservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.lawyerservice.domain.DTO.LawyerDTO;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.service.LawyerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawyer")
public class LawyerController {

    private final LawyerService lawyerService;

    ModelMapper modelMapper = new ModelMapper();

    private static final Logger LOGGER
            = LoggerFactory.getLogger(LawyerController.class);

    private final String NUMBER_VARIABLE_PATH = "lawyerId";
    private final String NAME_VARIABLE_PATH = "lawyerName";

    @Operation(summary = "It adds a new Lawyer to the database")
    @ApiResponse(responseCode = "201",
            description = "Add new Lawyer to the database",
            content = {@Content(mediaType =  "application/json")})
    @PostMapping
    ResponseEntity<LawyerDTO> createLawyer(@Valid @RequestBody LawyerDTO lawyerDTO){
        Lawyer added = lawyerService.addLawyer(modelMapper.map(lawyerDTO,Lawyer.class));
        LOGGER.info("Lawyer: {} was added tp the database!", added.getName());
        return new ResponseEntity<>(modelMapper.map(added,LawyerDTO.class),
                HttpStatus.valueOf(201));
    }

    @Operation(summary = "It brings one Lawyer by name from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get one Lawyer from the database",
                    content = {@Content(mediaType =  "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Lawyer was not found in database",
                    content = {@Content(mediaType =  "application/json")}),
    })
    @GetMapping("/getById/{lawyerId}")
    ResponseEntity<LawyerDTO> getLawyerById(@PathVariable(NUMBER_VARIABLE_PATH) String lawyerId) {
        LawyerDTO foundedById = modelMapper.map(lawyerService.getLawyerByID(lawyerId),LawyerDTO.class);
        LOGGER.info("Lawyer: {} was founded by id in the database!", foundedById.getName());
        return new ResponseEntity<>(foundedById, HttpStatus.valueOf(200));
    }

    @Operation(summary = "It brings one Lawyer by name from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get one Lawyer by name from the database",
                    content = {@Content(mediaType =  "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Lawyer was not found in database",
                    content = {@Content(mediaType =  "application/json")}),
    })
    @GetMapping("/getByName") // ?lawyerName=
    ResponseEntity<LawyerDTO> getLawyerByName(@RequestParam(NAME_VARIABLE_PATH) String lawyerName) {
        LawyerDTO foundedByName = modelMapper.map(lawyerService.getLawyerByName(lawyerName),LawyerDTO.class);
        LOGGER.info("Lawyer: {} was founded by name in the database!", foundedByName.getName());
        return new ResponseEntity<>(foundedByName, HttpStatus.valueOf(200));
    }

    @Operation(summary = "Takes all Lawyers from the database")
    @ApiResponse(responseCode = "200",
            description = "Gives all Lawyers from the database",
            content = {@Content(mediaType =  "application/json")})
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


    @Operation(summary = "It updates Lawyer by Id with the new data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Update Lawyer by Id to the database",
                    content = {@Content(mediaType =  "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Lawyer was not found in database",
                    content = {@Content(mediaType =  "application/json")}),
    })
    @PutMapping("/updateById/{lawyerId}")
    ResponseEntity<LawyerDTO> updateLawyerById(@PathVariable(NUMBER_VARIABLE_PATH) String lawyerId,
                                               @Valid @RequestBody Lawyer lawyer) {
        Lawyer updated = lawyerService.updateLawyerById(lawyerId, lawyer);
        LawyerDTO updatedDTO = modelMapper.map(updated, LawyerDTO.class);
        LOGGER.info("Lawyer: {} was updated tp the database!", lawyerId);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(summary = "It updates Lawyer by name with the new data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Update Lawyer by name to the database",
                    content = {@Content(mediaType =  "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Lawyer was not found in database",
                    content = {@Content(mediaType =  "application/json")}),
    })
    @PutMapping("/updateByName") // ?lawyerName=
    ResponseEntity<LawyerDTO> updateLawyerByName(@RequestParam String lawyerName,@Valid @RequestBody Lawyer lawyer){
        Lawyer updated = lawyerService.updateLawyerByName(lawyerName, lawyer);
        LawyerDTO updatedDTO = modelMapper.map(updated, LawyerDTO.class);
        LOGGER.info("Lawyer: {} was updated tp the database!", lawyerName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(summary = "It deletes one Lawyer by Id from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Deletes one Lawyer by Id from the database",
                    content = {@Content(mediaType =  "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Lawyer was not found in database",
                    content = {@Content(mediaType =  "application/json")}),
    })
    @DeleteMapping("/deleteById/{lawyerId}")
    ResponseEntity <LawyerDTO> deleteLawyerById(@PathVariable(NUMBER_VARIABLE_PATH) String lawyerId){
        LawyerDTO deleted = modelMapper.map(lawyerService.deleteLawyerById(lawyerId), LawyerDTO.class);
        LOGGER.info("Lawyer deleted: {}", deleted.getName());
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @Operation(summary = "It deletes one Lawyer by name from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Deletes one Lawyer by name from the database",
                    content = {@Content(mediaType =  "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Lawyer was not found in database",
                    content = {@Content(mediaType =  "application/json")}),
    })
    @DeleteMapping("/deleteByName") // ?lawyerName=
    ResponseEntity <LawyerDTO> deleteLawyerByName(@RequestParam String lawyerName){
        Lawyer deleted= lawyerService.deleteLawyerByName(lawyerName);
        LawyerDTO updatedDTO = modelMapper.map(deleted, LawyerDTO.class);
        LOGGER.info("Lawyer: {} was deleted tp the database!", lawyerName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(summary = "Delete all Lawyers from the database")
    @ApiResponse(responseCode = "200",
            description = "Delete all Lawyers from the database",
            content = {@Content(mediaType =  "application/json")})
    @DeleteMapping("/deleteAll")
    ResponseEntity <String> deleteAlLawyers(){
        lawyerService.deleteAlLawyers();
        LOGGER.info("Database is empty");
        return new ResponseEntity<>("Database is empty", HttpStatus.OK);
    }
}
