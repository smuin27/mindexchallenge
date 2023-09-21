package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.CompensationDetails;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationUrl;
    private String compensationIdUrl;
    private String employeeUrl;

    @Autowired
    private CompensationService compensationService;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
        employeeUrl = "http://localhost:" + port + "/employee";

    }

    @Test
    public void testCreateRead() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("Jack");
        testEmployee.setLastName("Alexander");
        testEmployee.setDepartment("Sales");
        testEmployee.setPosition("Manager");
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

        CompensationDetails testCompensationDetails = new CompensationDetails();
        testCompensationDetails.setEmployeeId(createdEmployee.getEmployeeId());
        testCompensationDetails.setSalary("100,000,000,000 USD");
        testCompensationDetails.setEffectiveDate(new Date());

        // Create checks
        CompensationDetails createdCompensationDetails = restTemplate.postForEntity(compensationUrl, testCompensationDetails, CompensationDetails.class).getBody();

        assertNotNull(createdCompensationDetails);
        assertEquals(testCompensationDetails.getSalary(), createdCompensationDetails.getSalary());
        assertEquals(testCompensationDetails.getEffectiveDate(), createdCompensationDetails.getEffectiveDate());

        // Read checks
        Compensation readCompensation = restTemplate.getForEntity(compensationIdUrl, Compensation.class, testCompensationDetails.getEmployeeId()).getBody();

        assertEquals(testCompensationDetails.getEmployeeId(), readCompensation.getEmployee().getEmployeeId());
        assertEquals(testCompensationDetails.getSalary(), readCompensation.getSalary());
        assertEquals(testCompensationDetails.getEffectiveDate(), readCompensation.getEffectiveDate());

    }
}
