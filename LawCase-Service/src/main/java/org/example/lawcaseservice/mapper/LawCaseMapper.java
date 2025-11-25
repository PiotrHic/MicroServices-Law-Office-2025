package org.example.lawcaseservice.mapper;

import org.example.lawcaseservice.domain.DTO.LawCaseDTO;
import org.example.lawcaseservice.domain.LawCase;
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
                .lawyerId(lawcase.getLawyerId())
                .lawyer(lawcase.getLawyer())
                .lawClient(lawcase.getLawClient())
                .LawClientId(lawcase.getLawClientId())
                .build();
    }

    public LawCase toEntity(LawCaseDTO dto) {
        if(dto == null) {
            return null;
        }
        return LawCase.builder()
                .id(dto.getId())
                .name(dto.getName())
                .lawyerId(dto.getLawyerId())
                .lawyer(dto.getLawyer())
                .lawClient(dto.getLawClient())
                .LawClientId(dto.getLawClientId())
                .build();
    }
}
