package org.example.lawclientservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.lawclientservice.domain.DTO.LawClientDTO;
import org.example.lawclientservice.domain.LawClient;
import org.example.lawclientservice.service.LawClientService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawclient")
public class LawClientController {

    private final LawClientService lawClientService;

    ModelMapper modelMapper;

    private static final Logger LOGGER
            = LoggerFactory.getLogger(LawClientController.class);

    private final String NUMBER_VARIABLE_PATH = "lawClientId";
    private final String NAME_VARIABLE_PATH = "lawClientName";

    @PostMapping
    ResponseEntity<LawClientDTO> createLawClient(@Valid @RequestBody LawClientDTO lawClientDTO){
        LawClient added = lawClientService.createLawClient(modelMapper.map(lawClientDTO,LawClient.class));
        LOGGER.info("LawClient: {} was added tp the database!", added.getName());
        return new ResponseEntity<>(modelMapper.map(added,LawClientDTO.class),
                HttpStatus.valueOf(201));
    }

    @GetMapping("/getById/{lawClientId}")
    ResponseEntity<LawClientDTO> getLawClientById(@PathVariable(NUMBER_VARIABLE_PATH) String lawClientId) {
        LawClientDTO foundedById = modelMapper.map(lawClientService.getLawClientByID(lawClientId),LawClientDTO.class);
        LOGGER.info("LawClient: {} was founded by id in the database!", foundedById.getName());
        return new ResponseEntity<>(foundedById, HttpStatus.valueOf(200));
    }

    @GetMapping("/getByName") // ?lawyerName=
    ResponseEntity<LawClientDTO> getLawClientByName(@RequestParam(NAME_VARIABLE_PATH) String lawClientName) {
        LawClientDTO foundedByName = modelMapper.map(lawClientService.getLawClientByName(lawClientName),LawClientDTO.class);
        LOGGER.info("LawClient: {} was founded by name in the database!", foundedByName.getName());
        return new ResponseEntity<>(foundedByName, HttpStatus.valueOf(200));
    }

    @GetMapping("/getAllLawClients")
    ResponseEntity<List<LawClientDTO>> getAllLawClients() {
        List<LawClient> lawClients = lawClientService.getAllLawClients();
        if (lawClients.isEmpty()) {
            throw new LayerInstantiationException("There is no law clients in the database!");
        }
        LOGGER.info("All law clients were founded!");
        List<LawClientDTO> lawClientsDTOs = lawClients.stream()
                .map(lawClient -> modelMapper.map(lawClient,LawClientDTO.class))
                .toList();
        return new ResponseEntity<>(lawClientsDTOs, HttpStatus.valueOf(200));
    }

    @PutMapping("/updateById/{lawClientId}")
    ResponseEntity<LawClientDTO> updateLawClientById(@PathVariable(NUMBER_VARIABLE_PATH) String lawClientId,
                                               @Valid @RequestBody LawClientDTO lawClientDTO) {
        LawClient toUpdate = modelMapper.map(lawClientDTO, LawClient.class);
        LawClient updated = lawClientService.updateLawClientById(lawClientId, toUpdate);
        LawClientDTO updatedDTO = modelMapper.map(updated, LawClientDTO.class);
        LOGGER.info("LawClient: {} was updated by id to the database!", lawClientId);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @PutMapping("/updateByName") // ?lawyerName=
    ResponseEntity<LawClientDTO> updateLawClientByName(@RequestParam String lawClientName,@Valid
    @RequestBody LawClientDTO lawClientDTO){
        LawClient toUpdate = modelMapper.map(lawClientDTO, LawClient.class);
        LawClient updated = lawClientService.updateLawClientByName(lawClientName, toUpdate);
        LawClientDTO updatedDTO = modelMapper.map(updated, LawClientDTO.class);
        LOGGER.info("LawClient: {} was updated by name to the database!", lawClientName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @DeleteMapping("/deleteById/{lawClientId}")
    ResponseEntity <LawClientDTO> deleteLawClientById(@PathVariable(NUMBER_VARIABLE_PATH) String lawClientId){
        LawClientDTO deleted = modelMapper.map(lawClientService.deleteLawClientById(lawClientId), LawClientDTO.class);
        LOGGER.info("LawClient deleted: {} by id from the database!", deleted.getName());
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }


    @DeleteMapping("/deleteByName") // ?lawyerName=
    ResponseEntity <LawClientDTO> deleteLawClientByName(@RequestParam String lawClientName){
        LawClient deleted = lawClientService.deleteLawClientByName(lawClientName);
        LawClientDTO updatedDTO = modelMapper.map(deleted, LawClientDTO.class);
        LOGGER.info("LawClient: {} was deleted by name from the database!", lawClientName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }


    @DeleteMapping("/deleteAll")
    ResponseEntity <String> deleteAllLawClients(){
        lawClientService.deleteAllLawClients();
        LOGGER.info("Database is empty");
        return new ResponseEntity<>("Database is empty", HttpStatus.OK);
    }


}
