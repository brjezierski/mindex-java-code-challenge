package com.mindex.challenge.data;

import java.util.*;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;

public class ReportingStructure {
    private Employee employee;
    private int numberOfReports;


    public ReportingStructure() {
    }

    public ReportingStructure(Employee employee, EmployeeRepository employeeRepository) {
        this.employee = employee;
        this.numberOfReports = 0;
        getDirectReports(employee, employeeRepository);
    }

    private void getDirectReports(Employee employee, EmployeeRepository employeeRepository) {
        // System.out.println("name: "+employee.getFirstName());

        List<Employee> employees = employee.getDirectReports();
        if (employees != null) {
            for (Employee report : employees) {
                String id = report.getEmployeeId();
                System.out.println("id: "+id);
                this.numberOfReports++;
                Employee nextEmployee = employeeRepository.findByEmployeeId(id);
                System.out.println("next.name: "+nextEmployee.getFirstName());
                getDirectReports(nextEmployee, employeeRepository);
            }
        }
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }

    public int getNumberOfReports() {
        return this.numberOfReports;
    }

}
