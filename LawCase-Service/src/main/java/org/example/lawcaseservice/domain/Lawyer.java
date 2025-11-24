package org.example.lawcaseservice.domain;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Lawyer {
    private String id;
    private String name;
}
