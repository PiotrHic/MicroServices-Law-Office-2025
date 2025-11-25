package org.example.lawyerservice.mapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.lawyerservice.domain.DTO.LawyerDTO;
import org.example.lawyerservice.domain.Lawyer;
import org.springframework.stereotype.Component;
@Component
public class LawyerMapper {

    public LawyerDTO toDTO(Lawyer lawyer) {
        if(lawyer == null) {
            return null;
        }
        return LawyerDTO.builder()
                .id(lawyer.getId())
                .name(lawyer.getName())
                .lawCaseList(lawyer.getLawCaseList())
                .build();
    }

    public Lawyer toEntity(LawyerDTO dto) {
        if(dto == null) {
            return null;
        }
        return Lawyer.builder()
                .id(dto.getId())
                .name(dto.getName())
                .lawCaseList(dto.getLawCaseList())
                .build();
    }
}
