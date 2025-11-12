package org.example.lawclientservice.service;

import org.example.lawclientservice.domain.LawClient;
import org.example.lawclientservice.exception.LawClientNotFoundException;
import org.example.lawclientservice.repository.LawClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class LawClientServiceTest {

    @Mock
    private LawClientRepository lawClientRepository;

    @InjectMocks
    private LawClientServiceImpl lawClientService;

    private LawClient lawClient;

    @BeforeEach
    void setUp() {
        lawClient = LawClient
                    .builder()
                    .id("1")
                    .name("name")
                    .build();
    }

    // ---------- CREATE ----------
    @Test
    void addLawCase_ShouldSaveAndReturnLawCase() {
        when(lawClientRepository.save(lawClient)).thenReturn(lawClient);

        LawClient result = lawClientService.createLawClient(lawClient);

        assertEquals(lawClient, result);
        verify(lawClientRepository, times(1)).save(lawClient);
    }

    // ---------- READ ----------
    @Test
    void getLawClientByID_ShouldReturnLawClient_WhenFound() {
        when(lawClientRepository.findLawClientById("L1")).thenReturn(Optional.of(lawClient));

        LawClient result = lawClientService.getLawClientByID("L1");

        assertEquals(lawClient, result);
        verify(lawClientRepository).findLawClientById("L1");
    }

    @Test
    void getLawClientByID_ShouldThrowException_WhenNotFound() {
        when(lawClientRepository.findLawClientById("L1")).thenReturn(Optional.empty());

        assertThrows(LawClientNotFoundException.class, ()
                -> lawClientService.getLawClientByID("L1"));
    }

    @Test
    void getLawClientByName_ShouldReturnLawClient_WhenFound() {
        when(lawClientRepository.findLawClientByName("name")).thenReturn(Optional.of(lawClient));

        LawClient result = lawClientService.getLawClientByName("name");

        assertEquals(lawClient, result);
        verify(lawClientRepository).findLawClientByName("name");
    }

    @Test
    void getLawClientByName_ShouldThrowException_WhenNotFound() {
        when(lawClientRepository.findLawClientByName("Unknown")).thenReturn(Optional.empty());

        assertThrows(LawClientNotFoundException.class, ()
                -> lawClientService.getLawClientByName("Unknown"));
    }

    @Test
    void getAllLawClients_ShouldReturnAllLawClients() {
        List<LawClient> list = Arrays.asList(lawClient);
        when(lawClientRepository.findAll()).thenReturn(list);

        List<LawClient> result = lawClientService.getAllLawClients();

        assertEquals(1, result.size());
        assertEquals("name", result.get(0).getName());
        verify(lawClientRepository).findAll();
    }

}

