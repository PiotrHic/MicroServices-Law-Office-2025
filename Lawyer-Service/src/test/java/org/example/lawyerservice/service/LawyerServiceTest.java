package org.example.lawyerservice.service;

import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.repository.LawyerRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LawyerServiceTest {

    @Mock
    LawyerRepository lawyerRepository;

    AutoCloseable autoCloseable;

    @InjectMocks
    LawyerServiceImpl lawyerService;

    Lawyer returned = Lawyer.builder()
            .id("1")
            .name("test1")
            .build();

    Lawyer first = Lawyer.builder()
            .id("1")
            .name("test2")
            .build();

    Lawyer second = Lawyer.builder()
            .id("1")
            .name("test3")
            .build();


    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        lawyerService = new LawyerServiceImpl(lawyerRepository);
        lawyerService.deleteAlLawyers();
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    @DisplayName("Add Lawyer Test")
    void addLawyerTest(){
        when(lawyerService.addLawyer(returned)).thenReturn(returned);
        Lawyer result = lawyerService.addLawyer(returned);
        Assertions.assertEquals(returned.getName(), result.getName());
    };

    @Test
    @DisplayName("Get Lawyer By Id Test")
    @Disabled
    void getLawyerByIdTest(String id){
        Lawyer result = lawyerService.addLawyer(returned);
        when(lawyerService.getLawyerByID(returned.getId())).thenReturn(result);
        Assertions.assertEquals(returned.getName(), result.getName());
    };

    @Test
    @DisplayName("Get Lawyer By Name Test")
    @Disabled
    void getLawyerByNameTest(String name){
        Lawyer result = lawyerService.addLawyer(returned);
        when(lawyerService.getLawyerByName(returned.getName())).thenReturn(returned);
        Assertions.assertEquals("test1", result.getName());
    };

    @Test
    @DisplayName("Get All Lawyers Test")
    @Disabled
    void getAllLawyersTest(){
        int repository_size = lawyerService.getAllLawyers().size();
        assertThat(repository_size).isZero();

        lawyerService.addLawyer(first);
        lawyerService.addLawyer(second);
        List<Lawyer> result = lawyerService.getAllLawyers();
        repository_size = lawyerService.getAllLawyers().size();
        assertThat(repository_size).isEqualTo(2);
    };

    @Test
    @DisplayName("Update Lawyer By Id Test")
    @Disabled
    void updateLawyerByIdTest(String id, Lawyer lawyer){
        Lawyer added = lawyerService.addLawyer(first);
        Lawyer updated = lawyerService.updateLawyerById(first.getId(),second);
        assertThat(first.getId()).isEqualTo(updated.getId());
        assertThat(first.getName()).isNotEqualTo(updated.getName());
    };

    @Test
    @DisplayName("Get Lawyer By Name Test")
    @Disabled
    void updateLawyerByNameTest(String name, Lawyer lawyer){
        Lawyer added = lawyerService.addLawyer(first);
        Lawyer toUpdate = new Lawyer("1", added.getName());
        Lawyer updated = lawyerService.updateLawyerByName(first.getName(),toUpdate);
        assertThat(added.getId()).isEqualTo(updated.getId());
        assertThat(first.getName()).isNotEqualTo(updated.getName());
    };

    @Test
    @DisplayName("Delete Lawyer By Id Test")
    @Disabled
    void deleteByIdTest(String id){
        Lawyer added = lawyerService.addLawyer(first);
        Lawyer result = lawyerService.deleteLawyerById(added.getId());
        assertThat(result).isEqualTo(added);
    };

    @Test
    @DisplayName("Delete Lawyer By Name Test")
    @Disabled
    void deleteLawyerByNameTest(String name){
        Lawyer added = lawyerService.addLawyer(first);
        Lawyer result = lawyerService.deleteLawyerByName(added.getName());
        assertThat(result).isEqualTo(added);
    };

    @Test
    @DisplayName("Delete All Lawyers Test")
    @Disabled
    void deleteAllTest(){
        int repository_size = lawyerService.getAllLawyers().size();
        assertThat(repository_size).isZero();

        lawyerService.addLawyer(first);
        lawyerService.addLawyer(second);
        List<Lawyer> result = lawyerService.getAllLawyers();
        repository_size = lawyerService.getAllLawyers().size();
        assertThat(repository_size).isEqualTo(2);

        lawyerService.deleteAlLawyers();
        repository_size = lawyerService.getAllLawyers().size();
        assertThat(repository_size).isEqualTo(0);
    };
}

