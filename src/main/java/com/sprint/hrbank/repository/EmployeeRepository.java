package com.sprint.hrbank.repository;

import com.sprint.hrbank.entity.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeRepository
        extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

    List<Employee> findByNameContainingOrEmailContaining(String nameKeyword, String emailKeyword);
}
