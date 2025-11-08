package org.example.lawyerservice.service;

import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.exception.LawyerNotFoundException;
import org.example.lawyerservice.repository.LawyerRepository;
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
class LawyerServiceImplTest {

    @Mock
    private LawyerRepository lawyerRepository;

    @InjectMocks
    private LawyerServiceImpl lawyerService;

    private Lawyer lawyer;

    @BeforeEach
    void setUp() {
        lawyer = new Lawyer();
        lawyer.setId("L1");
        lawyer.setName("John Doe");
    }

    // ---------- CREATE ----------
    @Test
    void addLawyer_ShouldSaveAndReturnLawyer() {
        when(lawyerRepository.save(lawyer)).thenReturn(lawyer);

        Lawyer result = lawyerService.addLawyer(lawyer);

        assertEquals(lawyer, result);
        verify(lawyerRepository, times(1)).save(lawyer);
    }

    // ---------- READ ----------
    @Test
    void getLawyerByID_ShouldReturnLawyer_WhenFound() {
        when(lawyerRepository.findLawyerById("L1")).thenReturn(Optional.of(lawyer));

        Lawyer result = lawyerService.getLawyerByID("L1");

        assertEquals(lawyer, result);
        verify(lawyerRepository).findLawyerById("L1");
    }

    @Test
    void getLawyerByID_ShouldThrowException_WhenNotFound() {
        when(lawyerRepository.findLawyerById("L1")).thenReturn(Optional.empty());

        assertThrows(LawyerNotFoundException.class, () -> lawyerService.getLawyerByID("L1"));
    }

    @Test
    void getLawyerByName_ShouldReturnLawyer_WhenFound() {
        when(lawyerRepository.findLawyerByName("John Doe")).thenReturn(Optional.of(lawyer));

        Lawyer result = lawyerService.getLawyerByName("John Doe");

        assertEquals(lawyer, result);
        verify(lawyerRepository).findLawyerByName("John Doe");
    }

    @Test
    void getLawyerByName_ShouldThrowException_WhenNotFound() {
        when(lawyerRepository.findLawyerByName("Unknown")).thenReturn(Optional.empty());

        assertThrows(LawyerNotFoundException.class, () -> lawyerService.getLawyerByName("Unknown"));
    }

    @Test
    void getAllLawyers_ShouldReturnAllLawyers() {
        List<Lawyer> list = Arrays.asList(lawyer);
        when(lawyerRepository.findAll()).thenReturn(list);

        List<Lawyer> result = lawyerService.getAllLawyers();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(lawyerRepository).findAll();
    }

    // ---------- UPDATE ----------
    @Test
    void updateLawyerById_ShouldUpdateFields() {
        Lawyer updated = new Lawyer();
        updated.setName("Updated Name");

        when(lawyerRepository.findLawyerById("L1")).thenReturn(Optional.of(lawyer));

        Lawyer result = lawyerService.updateLawyerById("L1", updated);

        assertEquals("Updated Name", result.getName());
        verify(lawyerRepository).findLawyerById("L1");
    }

    @Test
    void updateLawyerByName_ShouldUpdateFields() {
        Lawyer updated = new Lawyer();
        updated.setName("Updated Lawyer");

        when(lawyerRepository.findLawyerByName("John Doe")).thenReturn(Optional.of(lawyer));

        Lawyer result = lawyerService.updateLawyerByName("John Doe", updated);

        assertEquals("Updated Lawyer", result.getName());
        verify(lawyerRepository).findLawyerByName("John Doe");
    }

    // ---------- DELETE ----------
    @Test
    void deleteLawyerById_ShouldReturnDeletedLawyer_WhenFound() {
        when(lawyerRepository.deleteLawyerById("L1")).thenReturn(Optional.of(lawyer));

        Lawyer result = lawyerService.deleteLawyerById("L1");

        assertEquals(lawyer, result);
        verify(lawyerRepository).deleteLawyerById("L1");
    }

    @Test
    void deleteLawyerById_ShouldThrowException_WhenNotFound() {
        when(lawyerRepository.deleteLawyerById("L1")).thenReturn(Optional.empty());

        assertThrows(LawyerNotFoundException.class, () -> lawyerService.deleteLawyerById("L1"));
    }

    @Test
    void deleteLawyerByName_ShouldReturnDeletedLawyer_WhenFound() {
        when(lawyerRepository.deleteLawyerByName("John Doe")).thenReturn(Optional.of(lawyer));

        Lawyer result = lawyerService.deleteLawyerByName("John Doe");

        assertEquals(lawyer, result);
        verify(lawyerRepository).deleteLawyerByName("John Doe");
    }

    @Test
    void deleteLawyerByName_ShouldThrowException_WhenNotFound() {
        when(lawyerRepository.deleteLawyerByName("Unknown")).thenReturn(Optional.empty());

        assertThrows(LawyerNotFoundException.class, () -> lawyerService.deleteLawyerByName("Unknown"));
    }

    @Test
    void deleteAlLawyers_ShouldReturnConfirmationMessage() {
        String result = lawyerService.deleteAlLawyers();

        verify(lawyerRepository).deleteAll();
        assertEquals("All Lawyers were removed from database!", result);
    }
}

