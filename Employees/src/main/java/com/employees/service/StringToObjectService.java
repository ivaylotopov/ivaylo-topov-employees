package com.employees.service;

import com.employees.model.Employee;
import com.employees.model.Project;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class StringToObjectService {

    /**
     * e.g. 143(employeeID), 10(projectID), 2009-01-01(dateFrom), 2011-04-27(dateTo) -> Employee object
     * @param str comma separated string line with all properties needed for building single employee object
     * @return Employee object
     */
    public Employee fromStringToEmployee(String str) {
        try {
            String[] tokens = str.split(",");
            int employeeId = Integer.parseInt(tokens[0].trim());
            Project project = new Project(Integer.parseInt(tokens[1].trim()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Date dateFrom = sdf.parse(tokens[2].trim());
            Date dateTo;
            if (tokens[3].trim().equals("NULL")) {
                dateTo = sdf.parse(LocalDate.now().format(dtf));
            } else {
                dateTo = sdf.parse(tokens[3].trim());
            }
            return new Employee(employeeId, project, dateFrom, dateTo);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Invalid employee file.");
        }
    }
}
