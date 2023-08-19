package com.example.webfluxhttpexchange.service;

import com.example.webfluxhttpexchange.model.Employee;
import com.example.webfluxhttpexchange.model.JobType;
import com.example.webfluxhttpexchange.utility.EmployeeNotFoundException;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import reactor.test.StepVerifier;

import static com.example.webfluxhttpexchange.model.JobType.FULLTIME;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@SpringBootTest
@AutoConfigureWireMock(port = 0)
public class EmployeeManagementServiceTest {

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.application.employeeService.url", () -> "http://localhost:${wiremock.server.port}");
    }

    @Autowired
    @Qualifier(value = "employeeManagementWebClient") //employeeManagementHttpExchangeClient
    private EmployeeManagementService employeeManagementService;

    @Test
    @DisplayName("getEmployeeById Test")
    void test01() {
        stubFor(get(urlEqualTo("/employee/1"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, "application/json")
                        .withBody("""
                                {
                                    "id":"1",
                                    "name":"John Doe",
                                    "jobType":"FULLTIME"
                                }
                                """)
                )
        );

        StepVerifier
                .create(employeeManagementService.getEmployeeById(1))
                .consumeNextWith(employee -> {
                    assertNotNull(employee);
                    assertEquals(1, employee.id());
                    assertEquals("John Doe", employee.name());
                    assertEquals(FULLTIME, employee.jobType());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("getEmployeeById 404 Test")
    void test02() {
        stubFor(get(urlEqualTo("/employee/2"))
                .willReturn(aResponse()
                        .withStatus(404)
                )
        );

        StepVerifier
                .create(employeeManagementService.getEmployeeById(2))
                .expectErrorMatches(throwable -> throwable instanceof EmployeeNotFoundException)
                .verify();
    }

}
