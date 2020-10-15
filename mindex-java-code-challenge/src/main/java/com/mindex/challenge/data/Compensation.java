package com.mindex.challenge.data;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;
import java.time.Instant;

public class Compensation {

    /*
     * NOTE: Recognizing ambiguity in task 2
     *
     *  Need to know more information about `effectiveDate`
     * e.g. timezone, day light savings. Instant seems appropriate for moment/UTC.
     * Could have also used LocalDateTime or ZonedDateTime
     *
     */
    @DBRef(lazy = true)
    private Employee employee;
    private String salary;
    private Instant effectiveDate;

    public Compensation() {
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getSalary() {
        return this.salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Instant getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEffectiveDate(Instant effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Compensation employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public Compensation salary(String salary) {
        this.salary = salary;
        return this;
    }

    public Compensation effectiveDate(Instant effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Compensation)) {
            return false;
        }
        Compensation compensation = (Compensation) o;
        return Objects.equals(employee, compensation.employee) 
            && Objects.equals(salary, compensation.salary) 
            && Objects.equals(effectiveDate, compensation.effectiveDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, salary, effectiveDate);
    }

    @Override
    public String toString() {
        return "{" +
            " employee='" + getEmployee() + "'" +
            ", salary='" + getSalary() + "'" +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            "}";
    }
    
}
