package org.example.lawcaseservice.domain.DTO;

import lombok.*;
import org.example.lawcaseservice.domain.LawClient;
import org.example.lawcaseservice.domain.Lawyer;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class LawCaseDTO {

    private String id;
    private String name;
    private Integer lawyerId;
    private Integer LawClientId;
    private Lawyer lawyer;
    private LawClient lawClient;

    public LawCaseDTO(String name, Integer lawyerId, Integer lawClientId, Lawyer lawyer, LawClient lawClient) {
        this.name = name;
        this.lawyerId = lawyerId;
        LawClientId = lawClientId;
        this.lawyer = lawyer;
        this.lawClient = lawClient;
    }
}
