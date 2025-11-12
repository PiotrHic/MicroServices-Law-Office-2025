package org.example.lawclientservice.domain;

import org.example.lawclientservice.domain.DTO.LawClientDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LawClientDTOTest {

    static LawClientDTO lawClient;
    static List<LawCase> lawCases;
    private static final String TEST_NAME = "TEST";
    private static final String TEST_ID_1 = "1";
    private static final String TEST_ID_2 = "2";

    @BeforeAll
    public static void setUp() {
        lawClient = LawClientDTO
                    .builder()
                    .id(TEST_ID_1)
                    .name(TEST_NAME)
                    .build();
    }
    @Test
    public void setIdAndGetId() {

        lawClient.setId(TEST_ID_2);
        assertEquals(TEST_ID_2, lawClient.getId());

    }

    @Test
    public void setNameAndGetName() {
        String newName = "XXXXXXXXXXXX";
        lawClient.setName(newName);
        assertEquals(newName, lawClient.getName());

    }

    @Test
    public void setLawCasesAndGetLawCases(){

        //given
        lawCases = new ArrayList<>();
        lawClient.setLawCaseList(lawCases);
        assertEquals(0, lawClient.getLawCaseList().size());

        //when
        lawClient.getLawCaseList().add(new LawCase(TEST_ID_1 + "23", TEST_NAME+ " setLawCases1"));
        lawClient.getLawCaseList().add(new LawCase(TEST_ID_1 + "32", TEST_NAME+ " setLawCases2"));

        //then
        assertEquals(2, lawClient.getLawCaseList().size());
    }


    @AfterAll
    public static void finish() {
        lawClient = null;
        lawCases = null;
    }
}
