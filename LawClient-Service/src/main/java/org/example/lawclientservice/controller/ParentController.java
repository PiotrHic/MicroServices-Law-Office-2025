package org.example.lawclientservice.controller;

import lombok.AllArgsConstructor;
import org.example.lawclientservice.mapper.LawClientMapper;
import org.example.lawclientservice.service.LawClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class ParentController {

    final LawClientService lawClientService;
    final LawClientMapper lawClientMapper;
    static final Logger LOGGER
            = LoggerFactory.getLogger(WebClientController.class);
    final String NUMBER_VARIABLE_PATH = "lawClientId";
    final String NUMBER_QUERY_PATH = "/{lawClientId}";
    final String NAME_VARIABLE_PATH = "lawClientName";
}
