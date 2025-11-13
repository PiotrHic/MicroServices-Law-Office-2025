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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
