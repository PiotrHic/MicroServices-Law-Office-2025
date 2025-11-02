package org.example.lawyerservice.domain;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Lawyer {

    public Lawyer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private String id;
    private String name;
    private List<LawCase> lawCaseList; // for later
}
