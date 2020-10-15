package com.mindex.challenge.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Employee {
    @Id
    private String employeeId;
    private String firstName;
    private String lastName;
    private String position;
    private String department;

    @DBRef(lazy=true)
    private List<Employee> directReports;

    public Employee() {
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Employee> getDirectReports() {
        return this.directReports == null ? Collections.emptyList() : this.directReports;
    }

    public void setDirectReports(List<Employee> directReports) {
        this.directReports = directReports;
    }

    public Employee employeeId(String employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public Employee firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Employee lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Employee position(String position) {
        this.position = position;
        return this;
    }

    public Employee department(String department) {
        this.department = department;
        return this;
    }

    public Employee directReports(List<Employee> directReports) {
        this.directReports = directReports;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Employee)) {
            return false;
        }
        Employee employee = (Employee) o;
        return Objects.equals(employeeId, employee.employeeId) 
            && Objects.equals(firstName, employee.firstName) 
            && Objects.equals(lastName, employee.lastName) 
            && Objects.equals(position, employee.position) 
            && Objects.equals(department, employee.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, firstName, lastName, position, department, directReports);
    }

    @Override
    public String toString() {
        return "{" +
            " employeeId='" + getEmployeeId() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", position='" + getPosition() + "'" +
            ", department='" + getDepartment() + "'" +
            ", directReports='" + getDirectReports() + "'" +
            "}";
    }
    
}
