package org.example.lawyerservice.webclient;


import org.example.lawyerservice.domain.LawCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LawCaseWebClientIntegrationTest {

    @LocalServerPort
    int port;

    LawCaseWebClient client;

    @BeforeEach
    void setup() {
        RestClient restClient = RestClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();

        HttpServiceProxyFactory factory =
                HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                        .build();

        client = factory.createClient(LawCaseWebClient.class);
    }

    @Test
    void testFindLawCaseByLawyerId() {
        List<LawCase> result = client.findLawCasesByLawyerIdAndSendThem("L123");

        // Assert fields
        assertNotNull(result);
    }
}

