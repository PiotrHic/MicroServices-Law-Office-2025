package org.example.lawyerservice.mapper;

import org.example.lawyerservice.domain.DTO.LawCaseDTO;
import org.example.lawyerservice.domain.DTO.LawyerDTO;
import org.example.lawyerservice.domain.LawCase;
import org.example.lawyerservice.domain.Lawyer;
import org.springframework.stereotype.Component;

@Component
public class LawCaseMapper {

    public LawCaseDTO toDTO(LawCase lawcase) {
        if(lawcase == null) {
            return null;
        }
        return LawCaseDTO.builder()
                .id(lawcase.getId())
                .name(lawcase.getName())
                .lawClient(lawcase.getLawClient())
                .lawClientId(lawcase.getLawClientId())
                .build();
    }

    public LawCase toEntity(LawCaseDTO dto) {
        if(dto == null) {
            return null;
        }
        return LawCase.builder()
                .id(dto.getId())
                .name(dto.getName())
                .lawClient(dto.getLawClient())
                .lawClientId(dto.getLawClientId())
                .build();
    }

}
