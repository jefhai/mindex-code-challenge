package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.CompensationService;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.Instant;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String employeeUrl;
    private String createCompensationUrl;
    private String readCompensationUrl;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationService compensationService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Employee testEmployeeOne;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        createCompensationUrl = "http://localhost:" + port + "/compensation";
        readCompensationUrl = "http://localhost:" + port + "/compensation/{id}";

        testEmployeeOne = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
    }

    @After
    public void teardown() {
        employeeUrl = null;
        createCompensationUrl = null;
        readCompensationUrl = null;

        testEmployeeOne = null;
    }

    @Test
    public void testCreateReadUpdate() {
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployee(testEmployeeOne);
        testCompensation.setSalary("111,111,111 GPD");
        testCompensation.setEffectiveDate(Instant.parse("2020-10-20T00:00:00Z"));

        // Create checks
        ResponseEntity createdCompensationResponse = restTemplate.postForEntity(createCompensationUrl, testCompensation, Compensation.class);
        assertEquals(HttpStatus.OK, createdCompensationResponse.getStatusCode());
        Compensation createdCompensation = (Compensation)createdCompensationResponse.getBody();
        assertNotNull(createdCompensation);
        assertEquals(testCompensation, createdCompensation);

        // Read checks
        ResponseEntity readCompensationResponse = restTemplate.getForEntity(readCompensationUrl, Compensation.class, createdCompensation.getEmployee().getEmployeeId());
        assertEquals(HttpStatus.OK, readCompensationResponse.getStatusCode());
        Compensation readCompensation = (Compensation)readCompensationResponse.getBody();
        assertNotNull(readCompensation);
        assertEquals(readCompensation, createdCompensation);
    }

}
