package com.employees.service;

import com.employees.model.Employee;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class EmployeeOperations {

    /**
     * Method for finding how many days two employees have been worked together
     *
     * @param dateFrom1 First employee project starting date
     * @param dateTo1   First employee project completed date
     * @param dateFrom2 Second employee project starting date
     * @param dateTo2   Second employee project completed date
     * @return days worked together for current project
     */
    private long haveWorkedTogether(Date dateFrom1, Date dateTo1, Date dateFrom2, Date dateTo2){
        long daysWorkedTogether=0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        Date endDate;

        // Getting greater date between first employee starting date and second employee starting date
        startDate = dateFrom1.after(dateFrom2) ? dateFrom1 : dateFrom2;
        // Getting smaller date between first employee completed date and second employee completed date
        endDate = dateTo1.after(dateTo2)? dateTo2 : dateTo1;

        // If end date is after start date we can assume that the First Employee and the Second Employee have been worked together
        boolean haveWorkedTogether = endDate.after(startDate);
        if(haveWorkedTogether){
            daysWorkedTogether =
                    ChronoUnit.DAYS.between(LocalDate.parse(sdf.format(startDate), dtf),LocalDate.parse(sdf.format(endDate), dtf));
        }
        return daysWorkedTogether;
    }

    /**
     * @param employees List of all employees
     * @return Map with key-Project ID and value-List of all employees worked on this project
     */
    public Map<Integer, ArrayList<Employee>> getProjectsEmployees(List<Employee> employees){
        Map<Integer,ArrayList<Employee>> result = new LinkedHashMap<>();
        for (Employee employee : employees) {
            if(result.containsKey(employee.getProject().getId())){
                ArrayList<Employee> currentEmployees = result.get(employee.getProject().getId());
                currentEmployees.add(employee);
                result.put(employee.getProject().getId(),currentEmployees);
            }else{
                ArrayList<Employee> newArrList = new ArrayList<>();
                newArrList.add(employee);
                result.put(employee.getProject().getId(), newArrList);

            }
        }
        return result;
    }

    /**
     * @param projectEmployees Map with key-Project ID and value-List of all employees worked on this project
     * @return LinkedHashMap of all employee pairs and total amount of days, they have worked together on all projects
     */
    public LinkedHashMap<String, Long> getEmployeesPairs(Map<Integer,ArrayList<Employee>> projectEmployees){
        LinkedHashMap<String, Long> result = new LinkedHashMap<>();
        //Iterate all projects one by one
        for (Integer projectId : projectEmployees.keySet()) {
            List<Employee> employees = projectEmployees.get(projectId);
            // Iterate each employee working on the current project in order to check
            // whether or not worked with another employees on the same time
            for (Employee currentEmployee : employees) {
                // Get current employee ID and check if worked together with everybody else from the current project
                int employeeId = currentEmployee.getEmployeeId();
                for (Employee employee : employees) {
                    // Exclude the case when we have same employee IDs
                    if(employeeId != employee.getEmployeeId()) {
                        // Calculate working days together
                        long workingDaysTogetherForCurrentProject =
                                haveWorkedTogether(currentEmployee.getDateFrom(), currentEmployee.getDateTo(),
                                        employee.getDateFrom(), employee.getDateTo());
                        if (workingDaysTogetherForCurrentProject > 0){
                            // If we already have this employee pair, worked together from previous project,
                            // we just update the total working days together value
                            if (result.containsKey(employeeId + "-" + employee.getEmployeeId())) {
                                long totalDays = result.get(employeeId + "-" + employee.getEmployeeId());
                                totalDays = totalDays + workingDaysTogetherForCurrentProject;
                                result.put(employeeId + "-" + employee.getEmployeeId(), totalDays);
                            }
                            // We need to skip scenario of reverse ID record
                            else if (result.containsKey(employee.getEmployeeId() + "-" + employeeId)) {}
                            // We add new employee pair, in case we do not have any records
                            // for these employees whether they have worked together or not
                            else {
                                result.putIfAbsent(employeeId + "-" + employee.getEmployeeId(), workingDaysTogetherForCurrentProject);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
}
