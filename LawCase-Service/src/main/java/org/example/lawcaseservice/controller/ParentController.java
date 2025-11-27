package org.example.lawcaseservice.controller;

import lombok.AllArgsConstructor;
import org.example.lawcaseservice.controller.webclient.lawyer.LawyerWebClientController;
import org.example.lawcaseservice.mapper.LawCaseMapper;
import org.example.lawcaseservice.mapper.LawyerMapper;
import org.example.lawcaseservice.service.LawCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class ParentController {

    protected final LawCaseService lawCaseService;
    protected final LawCaseMapper lawCaseMapper;
    protected final LawyerMapper lawyerMapper;
    protected static final Logger LOGGER
            = LoggerFactory.getLogger(LawyerWebClientController.class);
    protected final String LAWCASE_NUMBER_VARIABLE_PATH = "lawCaseId";
    protected final String LAWCASE_NUMBER_QUERY_PATH = "/{lawCaseId}";
    protected final String NAME_VARIABLE_PATH = "lawCaseName";
    protected final String LAWYER_NAME_VARIABLE_PATH = "lawyerId";
    protected final String LAWYER_NUMBER_QUERY_PATH = "/{lawyerId}";
    protected final String LAWCLIENT_NAME_VARIABLE_PATH = "lawClientId";
    protected final String LAWCLIENT_NUMBER_QUERY_PATH = "/{lawClientId}";
    protected final String DESCRIPTION_404_ID = "LawCase was not found by id";
    protected final String DESCRIPTION_404_NAME = "LawCase was not found by name";
    protected final String DESCRIPTION_500_SHORT = "Some internal server error";
    protected final String DESCRIPTION_500_LONG = "Invalid input data or " + DESCRIPTION_500_SHORT;

}
