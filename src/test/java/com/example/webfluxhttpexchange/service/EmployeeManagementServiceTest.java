package com.example.webfluxhttpexchange.service;

import com.example.webfluxhttpexchange.models.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import reactor.test.StepVerifier;

import static com.example.webfluxhttpexchange.models.JobType.CONTRACTOR;
import static com.example.webfluxhttpexchange.models.JobType.FULLTIME;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@SpringBootTest
@AutoConfigureWireMock(port = 0)
public class EmployeeManagementServiceTest {

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.application.employeeService.url", () -> "http://localhost:${wiremock.server.port}");
    }

    @Autowired
    @Qualifier(value = "employeeManagementWebClient")
    //@Qualifier(value = "employeeManagementHttpExchangeClient")
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
                        .withBody("""
                                {
                                    "httpMethod":"GET",
                                    "requestUri":"/employee/2",
                                    "statusCode":"404",
                                    "message":"employee with id 2 not found"
                                }
                                """)
                )
        );

        StepVerifier
                .create(employeeManagementService.getEmployeeById(2))
                .expectError()
                .verify();
    }

    @Test
    @DisplayName("save Employee Test")
    void test03() {
        //when
        stubFor(post(urlEqualTo("/employee"))
                .withHeader(CONTENT_TYPE, equalTo("application/json"))
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
        //given
        StepVerifier
                .create(employeeManagementService.save(new Employee(0, "John Doe", FULLTIME)))
                //then
                .consumeNextWith(employee -> {
                    assertNotNull(employee);
                    assertEquals(1, employee.id());
                    assertEquals("John Doe", employee.name());
                    assertEquals(FULLTIME, employee.jobType());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("update Employee Test")
    void test04() {
        //when
        stubFor(put(urlEqualTo("/employee/1"))
                .withHeader(CONTENT_TYPE, equalTo("application/json"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, "application/json")
                        .withBody("""
                                {
                                    "id":"1",
                                    "name":"John Doe",
                                    "jobType":"CONTRACTOR"
                                }
                                """)
                )
        );
        //given
        StepVerifier
                .create(employeeManagementService.update(1, new Employee(1, "John Doe", CONTRACTOR)))
                //then
                .consumeNextWith(employee -> {
                    assertNotNull(employee);
                    assertEquals(1, employee.id());
                    assertEquals("John Doe", employee.name());
                    assertEquals(CONTRACTOR, employee.jobType());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("delete Employee Test")
    void test05() {
        //when
        stubFor(delete(urlEqualTo("/employee/1"))
                .willReturn(aResponse()
                        .withStatus(200))
        );
        //given
        StepVerifier
                .create(employeeManagementService.delete(1))
                .expectNextCount(0)
                .verifyComplete();
    }

}
