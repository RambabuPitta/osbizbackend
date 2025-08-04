package com.orionsolwings.osbiz.employee.management.service.implementation;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.orionsolwings.osbiz.employee.management.model.Employee;
import com.orionsolwings.osbiz.employee.management.repository.EmployeeRepository;
import com.orionsolwings.osbiz.employee.management.service.EmployeeService;
import com.orionsolwings.osbiz.util.ApiResponses;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public ApiResponses<Employee> createEmployee(Employee employee) {
        try {
            Employee saved = repository.save(employee);
            return new ApiResponses<>("Employee created successfully", "SUCCESS", null);
        } catch (DuplicateKeyException e) {
            return new ApiResponses<>("Duplicate entry: " + e.getMessage(), "FAIL", null);
        } catch (Exception e) {
            return new ApiResponses<>("Unexpected error: " + e.getMessage(), "ERROR", null);
        }
    }

    @Override
    public Employee getEmployeeById(String id) {
        return repository.findByEmployeeId(id).orElse(null);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public Employee updateEmployee(String id, Employee updatedEmployee) {
        Optional<Employee> optional = repository.findById(id);
        if (optional.isPresent()) {
            updatedEmployee.setEmployeeId(id); // Make sure the ID is retained
            return repository.save(updatedEmployee);
        }
        return null;
    }

    @Override
    public boolean deleteEmployee(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
