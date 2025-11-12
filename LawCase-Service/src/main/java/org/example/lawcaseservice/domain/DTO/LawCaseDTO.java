package org.example.lawcaseservice.domain.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.lawcaseservice.domain.LawClient;
import org.example.lawcaseservice.domain.Lawyer;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class LawCaseDTO {

    public LawCaseDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    private String id;
    @NotBlank(message = "Name is required!")
    @Size(min=4, message = "name must have at least 4 characters!")
    private String name;
    private String lawyerId;
    private String LawClientId;
    private Lawyer lawyer;
    private LawClient lawClient;

    public LawCaseDTO(String name, String lawyerId, String lawClientId, Lawyer lawyer, LawClient lawClient) {
        this.name = name;
        this.lawyerId = lawyerId;
        LawClientId = lawClientId;
        this.lawyer = lawyer;
        this.lawClient = lawClient;
    }
}
