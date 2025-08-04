package com.orionsolwings.osbiz.employee.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orionsolwings.osbiz.employee.management.model.Employee;
import com.orionsolwings.osbiz.employee.management.service.EmployeeService;
import com.orionsolwings.osbiz.util.ApiResponses;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<ApiResponses<Employee>> createEmployee(@RequestBody Employee employee) {
        ApiResponses<Employee> response = employeeService.createEmployee(employee);

        HttpStatus status;
        switch (response.getStatus()) {
            case "SUCCESS": status = HttpStatus.OK; break;
            case "FAIL": status = HttpStatus.CONFLICT; break;
            default: status = HttpStatus.INTERNAL_SERVER_ERROR; break;
        }

        return ResponseEntity.status(status).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponses<Employee>> getEmployeeById(@PathVariable String id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return ResponseEntity.status(404).body(new ApiResponses<>("Employee not found", "FAILED"));
        }
        return ResponseEntity.ok(new ApiResponses<>("Employee fetched successfully", "SUCCESS", employee));
    }

    @GetMapping
    public ResponseEntity<ApiResponses<List<Employee>>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(new ApiResponses<>("All employees fetched", "SUCCESS", employees));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponses<Employee>> updateEmployee(@PathVariable String id, @RequestBody Employee updatedEmployee) {
        Employee updated = employeeService.updateEmployee(id, updatedEmployee);
        if (updated == null) {
            return ResponseEntity.status(404).body(new ApiResponses<>("Employee not found", "FAILED"));
        }
        return ResponseEntity.ok(new ApiResponses<>("Employee updated successfully", "SUCCESS", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponses<Void>> deleteEmployee(@PathVariable String id) {
        boolean deleted = employeeService.deleteEmployee(id);
        if (!deleted) {
            return ResponseEntity.status(404).body(new ApiResponses<>("Employee not found", "FAILED"));
        }
        return ResponseEntity.ok(new ApiResponses<>("Employee deleted successfully", "SUCCESS", null));
    }
}
