package org.example.lawcaseservice.service;

import org.example.lawcaseservice.domain.LawCase;
import org.example.lawcaseservice.exception.LawCaseNotFoundException;
import org.example.lawcaseservice.repository.LawCaseRepository;
import org.example.lawyerservice.exception.LawyerNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LawCaseServiceTest {

    @Mock
    private LawCaseRepository lawCaseRepository;

    @InjectMocks
    private LawCaseServiceImpl lawCaseService;

    private LawCase lawCase;

    @BeforeEach
    void setUp() {
        lawCase = new LawCase();
        lawCase.setId("123");
        lawCase.setName("Contract Dispute");
    }

    @Test
    void createLawCase_ShouldSaveAndReturnLawCase() {
        when(lawCaseRepository.save(lawCase)).thenReturn(lawCase);

        LawCase result = lawCaseService.createLawCase(lawCase);

        assertEquals(lawCase, result);
        verify(lawCaseRepository, times(1)).save(lawCase);
    }

    @Test
    void getLawCaseById_ShouldReturnLawCase_WhenFound() {
        when(lawCaseRepository.findLawCaseById("123")).thenReturn(Optional.of(lawCase));

        LawCase result = lawCaseService.getLawCaseById("123");

        assertEquals(lawCase, result);
        verify(lawCaseRepository).findLawCaseById("123");
    }

    @Test
    void getLawCaseById_ShouldThrowException_WhenNotFound() {
        when(lawCaseRepository.findLawCaseById("123")).thenReturn(Optional.empty());

        assertThrows(LawCaseNotFoundException.class, () -> lawCaseService.getLawCaseById("123"));
    }

    @Test
    void getLawCaseByName_ShouldReturnLawCase_WhenFound() {
        when(lawCaseRepository.findLawCaseByName("Contract Dispute")).thenReturn(Optional.of(lawCase));

        LawCase result = lawCaseService.getLawCaseByName("Contract Dispute");

        assertEquals(lawCase, result);
        verify(lawCaseRepository).findLawCaseByName("Contract Dispute");
    }

    @Test
    void getLawCaseByName_ShouldThrowException_WhenNotFound() {
        when(lawCaseRepository.findLawCaseByName("Unknown Case")).thenReturn(Optional.empty());

        assertThrows(LawCaseNotFoundException.class, () -> lawCaseService.getLawCaseByName("Unknown Case"));
    }

    @Test
    void getAllLawCases_ShouldReturnAllCases() {
        List<LawCase> cases = Arrays.asList(lawCase);
        when(lawCaseRepository.findAll()).thenReturn(cases);

        List<LawCase> result = lawCaseService.getAllLawCases();

        assertEquals(1, result.size());
        assertEquals("Contract Dispute", result.get(0).getName());
        verify(lawCaseRepository).findAll();
    }

    @Test
    void updateLawCaseById_ShouldUpdateFields() {
        LawCase updated = new LawCase();
        updated.setName("Updated Case");

        when(lawCaseRepository.findLawCaseById("123")).thenReturn(Optional.of(lawCase));

        LawCase result = lawCaseService.updateLawCaseById("123", updated);

        assertEquals("Updated Case", result.getName());
        verify(lawCaseRepository).findLawCaseById("123");
    }

    @Test
    void deleteLawCaseById_ShouldReturnDeletedCase_WhenFound() {
        when(lawCaseRepository.deleteLawCaseById("123")).thenReturn(Optional.of(lawCase));

        LawCase result = lawCaseService.deleteLawCaseById("123");

        assertEquals(lawCase, result);
        verify(lawCaseRepository).deleteLawCaseById("123");
    }

    @Test
    void deleteLawCaseById_ShouldThrowException_WhenNotFound() {
        when(lawCaseRepository.deleteLawCaseById("123")).thenReturn(Optional.empty());

        assertThrows(LawyerNotFoundException.class, () -> lawCaseService.deleteLawCaseById("123"));
    }

    @Test
    void deleteLawCaseByName_ShouldReturnDeletedCase_WhenFound() {
        when(lawCaseRepository.deleteLawCaseByName("Contract Dispute")).thenReturn(Optional.of(lawCase));

        LawCase result = lawCaseService.deleteLawCaseByName("Contract Dispute");

        assertEquals(lawCase, result);
        verify(lawCaseRepository).deleteLawCaseByName("Contract Dispute");
    }

    @Test
    void deleteLawCaseByName_ShouldThrowException_WhenNotFound() {
        when(lawCaseRepository.deleteLawCaseByName("Unknown Case")).thenReturn(Optional.empty());

        assertThrows(LawyerNotFoundException.class, () -> lawCaseService.deleteLawCaseByName("Unknown Case"));
    }

    @Test
    void deleteAllLawCases_ShouldReturnConfirmationMessage() {
        String message = lawCaseService.deleteAllLawCases();

        verify(lawCaseRepository).deleteAll();
        assertEquals("All LawCases were removed from database!", message);
    }

}
