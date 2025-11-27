package org.example.lawclientservice.mapper;

import org.example.lawclientservice.domain.DTO.LawClientDTO;
import org.example.lawclientservice.domain.LawClient;
import org.springframework.stereotype.Component;

@Component
public class LawClientMapper {

    public LawClientDTO toDTO(LawClient lawClient) {
        if(lawClient == null) {
            return null;
        }
        return LawClientDTO.builder()
                .id(lawClient.getId())
                .name(lawClient.getName())
                .lawCaseList(lawClient.getLawCaseList())
                .build();
    }

    public LawClient toEntity(LawClientDTO dto) {
        if(dto == null) {
            return null;
        }
        return LawClient.builder()
                .id(dto.getId())
                .name(dto.getName())
                .lawCaseList(dto.getLawCaseList())
                .build();
    }

}
