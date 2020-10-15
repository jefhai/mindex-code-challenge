package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
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
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeServiceImplTest {

    private String employeeUrl;
    private String employeeIdUrl;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
    }

    @Test
    public void testCreateReadUpdate() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        // Create checks
        ResponseEntity createEmployeeResponse = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class);
        Employee createdEmployee = (Employee)createEmployeeResponse.getBody();
        assertEquals(HttpStatus.OK, createEmployeeResponse.getStatusCode());

        // the service generates a new employeeId
        assertNotNull(createdEmployee.getEmployeeId());
        assertNotEquals(testEmployee.getEmployeeId(), createdEmployee.getEmployeeId());
        
        testEmployee.setEmployeeId(createdEmployee.getEmployeeId());
        assertEquals(testEmployee, createdEmployee);

        // Read checks
        ResponseEntity readEmployeeResponse = restTemplate.getForEntity(employeeIdUrl, Employee.class, createdEmployee.getEmployeeId());
        Employee readEmployee = (Employee)readEmployeeResponse.getBody();
        assertEquals(HttpStatus.OK, readEmployeeResponse.getStatusCode());

        assertEquals(createdEmployee.getEmployeeId(), readEmployee.getEmployeeId());
        assertEquals(createdEmployee, readEmployee);

        // Update checks
        readEmployee.setFirstName("John John");
        readEmployee.setLastName("Doe Doe");
        readEmployee.setDepartment("Engineering Department");
        readEmployee.setPosition("Development Manager");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity updateEmployeeResponse = restTemplate.exchange(employeeIdUrl,
                                                            HttpMethod.PUT,
                                                            new HttpEntity<Employee>(readEmployee, headers),
                                                            Employee.class,
                                                            readEmployee.getEmployeeId());
        Employee updatedEmployee = (Employee)updateEmployeeResponse.getBody();
        assertEquals(HttpStatus.OK, updateEmployeeResponse.getStatusCode());

        assertEquals(readEmployee, updatedEmployee);
    }

}
