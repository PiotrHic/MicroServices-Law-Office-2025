package org.example.lawcaseservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class LawCaseServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
