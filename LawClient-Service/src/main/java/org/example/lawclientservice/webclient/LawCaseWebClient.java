package org.example.lawclientservice.client;

import org.example.lawclientservice.domain.LawCase;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface LawCaseClient {

    @GetExchange("/api/lawcase/webclient/toLawClient/{lawClientId}")
    public List<LawCase> findLawCaseByLawClientId(@PathVariable("lawClientId") String lawClientId);

    @GetExchange("/api/lawcase/webclient/forLawClient-withLawyer/{lawClientId}")
    public List<LawCase> findLawCaseWithLawyerByLawClientId(@PathVariable("lawClientId") String lawClientId);

}