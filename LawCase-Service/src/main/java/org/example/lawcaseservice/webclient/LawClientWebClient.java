package org.example.lawcaseservice.webclient;

import org.example.lawcaseservice.domain.LawClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface LawClientWebClient {

    @GetExchange("/api/lawclient/webclient/sendLawClient/{lawClientId}")
    public LawClient findLawClientByLawClientId(@PathVariable("lawClientId") String lawClientId);

}
