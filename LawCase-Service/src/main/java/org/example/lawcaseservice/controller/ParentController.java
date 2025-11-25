package org.example.lawcaseservice.controller;

import lombok.AllArgsConstructor;
import org.example.lawcaseservice.mapper.LawCaseMapper;
import org.example.lawcaseservice.service.LawCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class ParentController {

    final LawCaseService lawCaseService;
    final LawCaseMapper lawCaseMapper;
    static final Logger LOGGER
            = LoggerFactory.getLogger(WebClientController.class);
    final String NUMBER_VARIABLE_PATH = "lawCaseId";
    final String NUMBER_QUERY_PATH = "/{lawCaseId}";
    final String NAME_VARIABLE_PATH = "lawCaseName";

}
