package com.sprint.hrbank.repository;

import com.sprint.hrbank.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {}
