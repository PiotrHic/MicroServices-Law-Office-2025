package org.example.lawyerservice.domain.DTO;

import lombok.*;
import org.example.lawyerservice.domain.LawCase;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class LawyerDTO {

    public LawyerDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private String id;
    private String name;
    private List<LawCase> lawCaseList;
}
