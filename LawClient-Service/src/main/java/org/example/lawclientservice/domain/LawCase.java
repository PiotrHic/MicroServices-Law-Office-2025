package org.example.lawclientservice.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LawCase {

    private String id;
    private String name;
    private String lawyerId;
    private Lawyer lawyer;
}