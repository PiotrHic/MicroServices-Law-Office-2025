package org.example.lawcaseservice.webclient;

import org.example.lawcaseservice.domain.DTO.LawyerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface LawyerWebClient {

    @GetExchange("/api/lawyer/webclient/sendLawyerToLawCase/{lawyerId}")
    public ResponseEntity<LawyerDTO> getLawyerByLawyerId(@PathVariable("lawyerId") String lawyerId);

}