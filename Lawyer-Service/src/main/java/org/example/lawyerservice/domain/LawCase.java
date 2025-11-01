package org.example.lawyerservice.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LawCase {

    public LawCase(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private String id;
    private String name;
    private Integer lawClientId;
    private Object lawClient;
}
