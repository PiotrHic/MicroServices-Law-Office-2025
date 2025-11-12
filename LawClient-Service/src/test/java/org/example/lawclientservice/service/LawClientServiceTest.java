package org.example.lawclientservice.service;

import org.example.lawclientservice.domain.LawClient;
import org.example.lawclientservice.repository.LawClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class LawClientServiceTest {

    @Mock
    private LawClientRepository lawClientRepository;

    @InjectMocks
    private LawClientServiceImpl lawCaseService;

    private LawClient lawClient;

    @BeforeEach
    void setUp() {
        lawClient = LawClient
                    .builder()
                    .id("1")
                    .name("mame")
                    .build();
    }

    // ---------- CREATE ----------
    @Test
    void addLawCase_ShouldSaveAndReturnLawCase() {
        when(lawClientRepository.save(lawClient)).thenReturn(lawClient);

        LawClient result = lawCaseService.createLawClient(lawClient);

        assertEquals(lawClient, result);
        verify(lawClientRepository, times(1)).save(lawClient);
    }

}

