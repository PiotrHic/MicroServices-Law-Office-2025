package org.example.lawyerservice.domain.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.lawyerservice.domain.LawCase;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
    @Id
    private String id;
    @NotBlank(message = "Name is required!")
    @Size(min=4, message = "name must have at least 4 characters!")
    private String name;
    private List<LawCase> lawCaseList;
}
