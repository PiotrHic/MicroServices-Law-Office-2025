package org.example.lawclientservice.service;

import org.example.lawclientservice.domain.LawClient;

import java.util.List;

public interface LawClientService {

    // C
    LawClient createLawClient(LawClient lawClient);

    // R
    LawClient getLawClientByID (String lawClientId);
    LawClient getLawClientByName (String name);
    List<LawClient> getAllLawClients();

    // U
    LawClient updateLawClientById(String lawClientId, LawClient lawClient);
    LawClient updateLawClientByName(String name, LawClient lawClient);

    // D
    LawClient deleteLawClientById(String lawClientId);
    LawClient deleteLawClientByName(String name);
    String deleteAllLawClients();

}
