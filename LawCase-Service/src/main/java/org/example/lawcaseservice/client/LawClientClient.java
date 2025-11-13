package org.example.lawcaseservice.client;

import org.example.lawcaseservice.domain.LawClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface LawClientClient {

    @GetExchange("/api/lawclient/forLawCase/{lawClientId}")
    public LawClient findLawClientByLawClientId(@PathVariable("lawClientId") String lawClientId);

}
