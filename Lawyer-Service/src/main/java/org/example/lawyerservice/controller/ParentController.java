package org.example.lawyerservice.controller;

import lombok.AllArgsConstructor;
import org.example.lawyerservice.mapper.LawyerMapper;
import org.example.lawyerservice.service.LawyerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class ParentController {

    final LawyerService lawyerService;
    static final Logger LOGGER
            = LoggerFactory.getLogger(WebClientController.class);
    final LawyerMapper lawyerMapper;
    final String NUMBER_VARIABLE_PATH = "lawyerId";
    final String NUMBER_QUERY_PATH = "/{lawyerId}";
    final String NAME_VARIABLE_PATH = "lawyerName";
}
