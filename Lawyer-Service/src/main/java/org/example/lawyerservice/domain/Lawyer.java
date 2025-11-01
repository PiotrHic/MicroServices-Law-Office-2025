package org.example.lawyerservice.domain;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lawyer {

    public Lawyer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    private Integer id;
    private String name;
    private List<Object> lawCaseList; // for later
}
