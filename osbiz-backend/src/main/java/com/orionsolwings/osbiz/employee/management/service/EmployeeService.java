package com.orionsolwings.osbiz.employee.management.service;


import com.orionsolwings.osbiz.employee.management.model.Employee;
import com.orionsolwings.osbiz.util.ApiResponses;

import java.util.List;

public interface EmployeeService {
    ApiResponses<Employee> createEmployee(Employee employee);
    Employee getEmployeeById(String id);
    List<Employee> getAllEmployees();
    Employee updateEmployee(String id, Employee employee);
    boolean deleteEmployee(String id);
}
