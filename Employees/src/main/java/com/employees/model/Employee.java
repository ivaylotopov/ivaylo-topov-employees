package com.employees.model;

import java.util.Date;

public class Employee {
    int employeeId;
    Project project;
    Date dateFrom;
    Date dateTo;

    public Employee(int employeeId, Project project, Date dateFrom, Date dateTo) {
        this.employeeId = employeeId;
        this.project = project;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public Project getProject() {
        return project;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }
}
