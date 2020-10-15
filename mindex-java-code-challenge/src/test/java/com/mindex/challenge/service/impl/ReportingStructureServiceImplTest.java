package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
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
public class ReportingStructureServiceImplTest {

    private String employeeUrl;
    private String readReportingStructureUrl;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ReportingStructureService resportStructureService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Employee testEmployeeOne;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        readReportingStructureUrl = "http://localhost:" + port + "/reportingStructure/{id}";

        testEmployeeOne = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
    }

    @After
    public void teardown() {
        employeeUrl = null;
        readReportingStructureUrl = null;

        testEmployeeOne = null;
    }

    @Test
    public void testCreateReadUpdate() {
        ReportingStructure testReportingStructure = new ReportingStructure(testEmployeeOne, 4);

        // Read checks
        ResponseEntity readReportingStructureResponse = restTemplate.getForEntity(readReportingStructureUrl, ReportingStructure.class, testEmployeeOne.getEmployeeId());
        assertEquals(HttpStatus.OK, readReportingStructureResponse.getStatusCode());
        ReportingStructure readReportingStructure = (ReportingStructure)readReportingStructureResponse.getBody();
        assertNotNull(readReportingStructure);
        assertEquals(readReportingStructure, testReportingStructure);
    }

}
