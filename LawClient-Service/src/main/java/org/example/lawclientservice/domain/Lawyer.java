package org.example.lawclientservice.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lawyer {

    private String id;
    private String name;
}