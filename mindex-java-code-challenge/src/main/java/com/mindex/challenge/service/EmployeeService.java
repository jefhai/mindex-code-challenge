package com.mindex.challenge.service;

import com.mindex.challenge.data.Employee;

public interface EmployeeService {
    Employee create(Employee employee);
    Employee read(String employeeId);
    Employee update(Employee employee);

    int getNumberOfReports(String employeeId);
}
