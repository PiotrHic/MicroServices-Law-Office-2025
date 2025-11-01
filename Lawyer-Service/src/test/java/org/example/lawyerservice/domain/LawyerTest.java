package org.example.lawyerservice.domain;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LawyerTest {

    static List<LawCase> lawCases;

    static Lawyer lawyer;

    static LawCase lawCase1;

    static LawCase lawCase2;

    private static final String TEST_NAME = "TEST";
    private static final String TEST_ID_1 = "1";
    private static final String TEST_ID_2 = "2";

    @BeforeAll
    public static void setUp() {
        lawyer = Lawyer.builder()
                .id(TEST_ID_1)
                .name(TEST_NAME)
                .lawCaseList(lawCases)
                .build();
        lawCases = new ArrayList<>();

        lawCase1 = LawCase.builder()
                .id(TEST_ID_1)
                .name(TEST_NAME + "1")
                .lawClientId(null)
                .lawClient(null)
                .build();

        lawCase2 = LawCase.builder()
                .id(TEST_ID_2)
                .name(TEST_NAME + "2")
                .lawClientId(null)
                .lawClient(null)
                .build();

        lawCases.add(lawCase1);
        lawCases.add(lawCase1);

        lawyer.setLawCaseList(lawCases);
    }
    @Test
    public void setId() {

        //given
        lawyer.setId(TEST_ID_1);
        assertEquals(TEST_ID_1, lawyer.getId());

        //when
        lawyer.setId(TEST_ID_1 + "1");

        //then
        assertEquals(TEST_ID_1 + "1", lawyer.getId());
    }
    @Test
    public void getId(){

        //given
        lawyer.setId(TEST_ID_1);
        assertEquals(TEST_ID_1, lawyer.getId());

        //when
        lawyer.setId(TEST_ID_1 + "1");

        //then
        assertEquals(TEST_ID_1 + "1", lawyer.getId());
    }
    @Test
    public void setName() {

        //given
        lawyer.setName(TEST_NAME);
        assertEquals(TEST_NAME, lawyer.getName());

        //when
        lawyer.setName(TEST_NAME+ "99");

        //then
        assertEquals(TEST_NAME+ "99", lawyer.getName());
    }
    @Test
    public void getName() {

        //given
        lawyer.setName(TEST_NAME);
        assertEquals(TEST_NAME, lawyer.getName());

        //when
        lawyer.setName(TEST_NAME+ "99");

        //then
        assertEquals(TEST_NAME+ "99", lawyer.getName());
    }
    @Test
    public void setLawCases(){

        //given
        lawCases = new ArrayList<>();
        lawyer.setLawCaseList(lawCases);
        assertEquals(0, lawyer.getLawCaseList().size());

        //when
        lawyer.getLawCaseList().add(new LawCase(TEST_ID_1 + "23", TEST_NAME+ " setLawCases1"));
        lawyer.getLawCaseList().add(new LawCase(TEST_ID_1 + "32", TEST_NAME+ " setLawCases2"));

        //then
        assertEquals(2, lawyer.getLawCaseList().size());
    }
    @Test
    public void getLawCases(){

        //given
        lawCases = new ArrayList<>();
        lawyer.setLawCaseList(lawCases);
        assertEquals(0, lawyer.getLawCaseList().size());

        //when
        lawyer.getLawCaseList().add(new LawCase(TEST_ID_1 + "23", TEST_NAME+ " setLawCases1"));
        lawyer.getLawCaseList().add(new LawCase(TEST_ID_1 + "32", TEST_NAME+ " setLawCases2"));
        //then

        assertEquals(2, lawyer.getLawCaseList().size());
    }

    @AfterAll
    public static void finish() {
        lawCases = null;
        lawyer = null;
        lawCase1 = null;
        lawCase2 = null;
    }
}
