package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee: [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String employeeId) {
        LOG.debug("Reading employee with employeeId: [{}]", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee: [{}]", employee);

        return employeeRepository.save(employee);
    }

    /*
     * NOTE: Could have also done this with Java Stream. Circular dependencies are an issue.
     * Would need to prevent them from being created during employee create and update.
     * Or use a @OneToMany and other Spring Boot techniques w/ beans
     */
    public int getNumberOfReports(String employeeId) {
        int accumulatedTotal = 0;

        // Ideally this wouldn't happen, hopefully source doesn't give us nulls in List<>
        Employee employee = this.read(employeeId);
        if (employee == null) {
            throw new RuntimeException("Null employee!");
        }

        List<Employee> reports = employee.getDirectReports();
        if (reports != null) {
            for (Employee reportingEmployee : reports) {
                accumulatedTotal += 1 + getNumberOfReports(reportingEmployee.getEmployeeId());
            }
        }
        
        return accumulatedTotal;
    }

}
