package com.employees;

import com.employees.model.Employee;
import com.employees.service.EmployeeOperations;
import com.employees.service.FileOperations;

import java.util.*;

public class Main{

    private static final String EMPLOYEE_FILE_NAME = "./Employees/Employees.txt";

    public static void main(String[] args)  {
        FileOperations fileOperations = new FileOperations();
        EmployeeOperations employeeOperations = new EmployeeOperations();

        List<Employee> employees = fileOperations.readFromFile(EMPLOYEE_FILE_NAME);
        Map<Integer,ArrayList<Employee>> projectsEmployees = employeeOperations.getProjectsEmployees(employees);
        LinkedHashMap<String, Long> employeesPairs = employeeOperations.getEmployeesPairs(projectsEmployees);

        employeesPairs.entrySet()
                .stream()
                .sorted((s1,s2)-> Long.compare(s2.getValue(),s1.getValue()))
                .limit(1)
                .forEach((emp)-> System.out.printf("The pair who have worked longest time together is: %s, work days: %.0f"
                        ,emp.getKey(),(double)emp.getValue()));
    }
}
