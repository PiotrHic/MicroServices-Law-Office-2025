package org.example.lawclientservice.controller;

import lombok.AllArgsConstructor;
import org.example.lawclientservice.service.LawClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawcclient")
public class LawClientController {

    private final LawClientService lawClientService;

    ModelMapper modelMapper;

    private static final Logger LOGGER
            = LoggerFactory.getLogger(LawyerController.class);

    private final String NUMBER_VARIABLE_PATH = "lawcaseId";
    private final String NAME_VARIABLE_PATH = "lawcaseName";
}
