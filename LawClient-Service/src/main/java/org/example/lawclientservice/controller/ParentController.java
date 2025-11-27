package org.example.lawclientservice.controller;

import lombok.AllArgsConstructor;
import org.example.lawclientservice.controller.webclient.LawClientWebClientController;
import org.example.lawclientservice.mapper.LawClientMapper;
import org.example.lawclientservice.service.LawClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class ParentController {

    protected final LawClientService lawClientService;
    protected final LawClientMapper lawClientMapper;
    protected static final Logger LOGGER
            = LoggerFactory.getLogger(LawClientWebClientController.class);
    protected final String NUMBER_VARIABLE_PATH = "lawClientId";
    protected final String NUMBER_QUERY_PATH = "/{lawClientId}";
    protected final String NAME_VARIABLE_PATH = "lawClientName";
    protected final String DESCRIPTION_404_ID = "LawClient was not found by id";
    protected final String DESCRIPTION_404_NAME = "LawClient was not found by name";
    protected final String DESCRIPTION_500_SHORT = "Some internal server error";
    protected final String DESCRIPTION_500_LONG = "Invalid input data or " + DESCRIPTION_500_SHORT;
}
