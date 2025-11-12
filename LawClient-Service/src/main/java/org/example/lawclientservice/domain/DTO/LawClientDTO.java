package org.example.lawclientservice.domain.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.lawclientservice.domain.LawCase;
import org.springframework.data.annotation.Id;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class LawClientDTO {

    public LawClientDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }
    @Id
    private String id;
    @NotBlank(message = "Name is required!")
    @Size(min=4, message = "name must have at least 4 characters!")
    private String name;
    private List<LawCase> lawCaseList;
}
