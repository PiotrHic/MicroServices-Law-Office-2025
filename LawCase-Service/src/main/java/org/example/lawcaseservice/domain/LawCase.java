package org.example.lawcaseservice.domain;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Document(collection = "LawCases")
public class LawCase {

    private String id;
    private String name;
    private Integer lawyerId;
    private Integer LawClientId;
    private Lawyer lawyer;
    private LawClient lawClient;

    public LawCase(String name, Integer lawyerId, Integer lawClientId, Lawyer lawyer, LawClient lawClient) {
        this.name = name;
        this.lawyerId = lawyerId;
        LawClientId = lawClientId;
        this.lawyer = lawyer;
        this.lawClient = lawClient;
    }
}
