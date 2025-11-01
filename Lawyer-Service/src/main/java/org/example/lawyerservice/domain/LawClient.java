package org.example.lawyerservice.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LawClient {

    private String id;
    private String name;
}