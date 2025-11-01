package org.example.lawyerservice.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LawClient {

    private Integer id;
    private String name;
}