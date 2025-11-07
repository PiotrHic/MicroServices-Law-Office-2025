package org.example.lawcaseservice.service;

import com.example.lawcaseservice.domain.LawCase;
import com.example.lawcaseservice.repository.InMemoryRepository;
import org.example.lawcaseservice.repository.LawCaseRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LawCaseServiceTest {


    @Mock
    LawCaseRepository lawCaseRepository;

    AutoCloseable autoCloseable;

    @InjectMocks
    LawCaseServiceImpl lawCaseService;

    LawCase returned= LawCase.builder()
            .id(1)
            .name("test1")
            .build();

    LawCase first = LawCase.builder()
            .id(2)
            .name("test2")
            .build();

    LawCase second = LawCase.builder()
            .id(3)
            .name("test3")
            .build();

    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        lawCaseService = new LawCaseServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void createLawCase(){

        //stub the data
        when(repository.addLawCase(returned)).thenReturn(returned);

        LawCase result = lawCaseService.createLawCase(returned);
        Assertions.assertEquals("test1", result.getName());
    }


    @Disabled
    @Test
    void updateLawCase(){


        //stub the data
        when(repository.getLawCaseByID(first.getId())).thenReturn(first);
        when(repository.addLawCase(first)).thenReturn(first);

        lawCaseService.createLawCase(first);

        LawCase result = lawCaseService.updateLawCase(second.getId(), second);
        Assertions.assertEquals("test2", result.getName());

    }

    @Disabled
    @Test
    public void getOneLawCase() throws Exception{

        //stub the data
        when(repository.getLawCaseByID(returned.getId())).thenReturn(returned);
        LawCase result = lawCaseService.getLawCase(returned.getId());
        Assertions.assertEquals("test", result.getName());
    }

    @Disabled
    @Test
    void getAllLawCases(){
        // when
//        when(repository.getAllLawCases()).
//                thenReturn(Arrays.asList(first,
//                        second));

        //then
        Set<LawCase> lawCases = lawCaseService.getAllLawCases();
        Assertions.assertEquals(lawCases.size(), 2);
    }

    @Disabled
    @Test
    void deleteLawCaseById(){

        when(repository.getLawCaseByID(returned.getId())).thenReturn(returned);
        String result = lawCaseService.deleteLawCaseByID(returned.getId());
        Assertions.assertEquals(result, isNotNull());
    }
    @Disabled
    @Test
    void deleteAllLawCases(){

        when(repository.getLawCaseByID(returned.getId())).thenReturn(returned);
        String result = lawCaseService.deleteAllLawCases();
        Assertions.assertEquals(result, isNotNull());
    }
}
