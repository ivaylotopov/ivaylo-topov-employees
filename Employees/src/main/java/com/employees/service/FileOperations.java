package com.employees.service;

import com.employees.model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileOperations {

    private StringToObjectService stringToObjectService = new StringToObjectService();

    /**
     * @param EMPLOYEE_FILE_NAME name of the source file
     * @return List of all employees POJOs read from the source file
     */
    public List<Employee> readFromFile(final String EMPLOYEE_FILE_NAME){
        List<Employee> employees = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(EMPLOYEE_FILE_NAME))) {
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                Employee employee = stringToObjectService.fromStringToEmployee(str);
                employees.add(employee);
            }
            return employees;

        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("File Read Error");
        }
    }
}
