package org.example.lawyerservice.controller;

import lombok.AllArgsConstructor;
import org.example.lawyerservice.controller.webclient.LawyerWebClientController;
import org.example.lawyerservice.mapper.LawCaseMapper;
import org.example.lawyerservice.mapper.LawyerMapper;
import org.example.lawyerservice.service.LawyerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class ParentController {

    protected final LawyerService lawyerService;
    protected static final Logger LOGGER
            = LoggerFactory.getLogger(LawyerWebClientController.class);
    protected final LawyerMapper lawyerMapper;
    protected final LawCaseMapper lawCaseMapper;
    protected final String NUMBER_VARIABLE_PATH = "lawyerId";
    protected final String NUMBER_QUERY_PATH = "/{lawyerId}";
    protected final String NAME_VARIABLE_PATH = "lawyerName";
    protected final String DESCRIPTION_404_ID = "Lawyer was not found by id";
    protected final String DESCRIPTION_404_NAME = "Lawyer was not found by name";
    protected final String DESCRIPTION_500_SHORT = "Some internal server error";
    protected final String DESCRIPTION_500_LONG = "Invalid input data or " + DESCRIPTION_500_SHORT;
}
