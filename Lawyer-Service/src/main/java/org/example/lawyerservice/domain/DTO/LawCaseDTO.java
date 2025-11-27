package org.example.lawyerservice.domain.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class LawCaseDTO {

    public LawCaseDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private String id;
    private String name;
    private String lawClientId;
    private Object lawClient;
}

