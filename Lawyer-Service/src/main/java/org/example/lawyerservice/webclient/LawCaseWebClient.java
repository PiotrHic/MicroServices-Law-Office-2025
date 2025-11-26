package org.example.lawyerservice.webclient;

import org.example.lawyerservice.domain.DTO.LawCaseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface LawCaseWebClient {

    static String ID = "lawyerId";
    static String ID_PATH = "/{lawyerId}";

    @GetExchange("/api/lawcase/webclient/sendLawCases" + ID_PATH)
    public List<LawCaseDTO> getLawCasesByLawyerId(@PathVariable(ID) String lawyerId);

    @GetExchange("/api/lawcase/webclient/sendLawCases-WithLawClients" + ID_PATH)
    public List<LawCaseDTO> getLawCasesWithLawClientsByLawyerId(@PathVariable(ID) String lawyerId);

}