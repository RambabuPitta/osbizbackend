package com.orionsolwings.osbiz.employee.management.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.orionsolwings.osbiz.employee.management.model.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

	Optional<Employee> findByEmployeeId(String id);
    // You can define custom query methods here if needed
}
