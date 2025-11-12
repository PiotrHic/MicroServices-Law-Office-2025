package org.example.lawclientservice.service;

import lombok.AllArgsConstructor;
import org.example.lawclientservice.domain.LawClient;
import org.example.lawclientservice.exception.LawClientNotFoundException;
import org.example.lawclientservice.repository.LawClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LawClientServiceImpl implements LawClientService {

    private final LawClientRepository lawClientRepository;
    @Override
    public LawClient createLawClient(LawClient LawClient) {
        return lawClientRepository.save(LawClient);
    }

    @Override
    public LawClient getLawClientByID(String lawClientId) {
        return lawClientRepository
                .findLawClientById(lawClientId)
                .orElseThrow(() -> new LawClientNotFoundException("LawCase with id: " + lawClientId + " was not found!"));
    }

    @Override
    public LawClient getLawClientByName(String name) {
        return lawClientRepository
                .findLawClientByName(name)
                .orElseThrow(() -> new LawClientNotFoundException("LawCase with name: " + name + " was not found!"));
    }

    @Override
    public List<LawClient> getAllLawClients() {
        return lawClientRepository.findAll();
    }

    @Override
    public LawClient updateLawClientById(String lawClientId, LawClient lawClient) {
        return null;
    }

    @Override
    public LawClient updateLawClientByName(String name, LawClient lawClient) {
        return null;
    }

    @Override
    public String deleteLawClientById(String lawClientId) {
        return "";
    }

    @Override
    public String deleteLawClientByName(String name) {
        return "";
    }

    @Override
    public String deleteAllLawClients() {
        return "";
    }
}
