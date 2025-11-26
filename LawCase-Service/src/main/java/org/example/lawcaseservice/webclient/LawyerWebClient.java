package org.example.lawcaseservice.client;


import org.example.lawcaseservice.domain.Lawyer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface LawyerClient {

    @GetExchange("/api/lawyer/webclient/sendLawyerToLawCase/{lawyerId}")
    public Lawyer getLawyerByLawyerId(@PathVariable("lawyerId") String lawyerId);

}