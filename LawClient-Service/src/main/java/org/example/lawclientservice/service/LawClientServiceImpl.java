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
                .orElseThrow(() -> new LawClientNotFoundException("LawClient with id: " + lawClientId + " was not found!"));
    }

    @Override
    public LawClient getLawClientByName(String name) {
        return lawClientRepository
                .findLawClientByName(name)
                .orElseThrow(() -> new LawClientNotFoundException("LawClient with name: " + name + " was not found!"));
    }

    @Override
    public List<LawClient> getAllLawClients() {
        return lawClientRepository.findAll();
    }

    @Override
    public LawClient updateLawClientById(String lawClientId, LawClient lawClient) {
        LawClient toUpdate = getLawClientByID(lawClientId);
        toUpdate.setId(lawClientId);
        toUpdate.setName(lawClient.getName());
        toUpdate.setLawCaseList(lawClient.getLawCaseList());
        lawClientRepository.deleteLawClientById(lawClientId);
        lawClientRepository.save(toUpdate);
        return toUpdate;
    }

    @Override
    public LawClient updateLawClientByName(String name, LawClient lawClient) {
        LawClient toUpdate = getLawClientByName(name);
        toUpdate.setId(lawClient.getId());
        toUpdate.setName(lawClient.getName());
        toUpdate.setLawCaseList(lawClient.getLawCaseList());
        lawClientRepository.deleteLawClientById(toUpdate.getId());
        lawClientRepository.save(toUpdate);
        return toUpdate;
    }

    @Override
    public LawClient deleteLawClientById(String lawClientId) {
        return lawClientRepository
                .deleteLawClientById(lawClientId)
                .orElseThrow(() -> new LawClientNotFoundException("LawClient with id: " + lawClientId + " was not found!"))
        ;
    }

    @Override
    public LawClient deleteLawClientByName(String name) {
        return lawClientRepository
                .deleteLawClientByName(name)
                .orElseThrow(() -> new LawClientNotFoundException("LawClient with name: " + name + " was not found!"));
    }

    @Override
    public String deleteAllLawClients() {
        lawClientRepository.deleteAll();
        return "All LawClients were removed from database!";
    }
}
