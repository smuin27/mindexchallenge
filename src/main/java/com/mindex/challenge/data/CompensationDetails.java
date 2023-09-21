package com.mindex.challenge.data;

import java.util.Date;

/*
    I created this type to use for storing compensation in the database. In my solution I only wanted to store
    the salary and effective date for each employee since the employee info was already being stored
 */
public class CompensationDetails {
    private String employeeId;
    private String salary;
    private Date effectiveDate;

    public CompensationDetails() {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}


