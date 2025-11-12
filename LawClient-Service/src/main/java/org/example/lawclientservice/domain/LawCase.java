package org.example.lawclientservice.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LawCase {

    private String id;
    private String name;
}