package org.example.lawyerservice.client;

import org.example.lawyerservice.domain.LawCase;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface LawCaseClient {

    static String ID = "lawyerId";
    static String ID_PATH = "/{lawyerId}";

    @GetExchange("/api/lawcase/webclient/forLawyer" + ID_PATH)
    public List<LawCase> findLawCasesByLawyerIdAndSendThem(@PathVariable(ID) String lawyerId);

    @GetExchange("/api/lawcase/webclient/getLawCases-withLawClient" + ID_PATH)
    public List<LawCase> bringLawCaseWithLawClientsByLawyerId(@PathVariable(ID) String lawyerId);

    @GetExchange("/api/lawcase/webclient/testToLawyerService" )
    public String testToTakeFromLawCaseService();

}