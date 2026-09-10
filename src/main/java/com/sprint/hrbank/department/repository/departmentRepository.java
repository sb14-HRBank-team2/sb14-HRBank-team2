package com.sprint.hrbank.department.repository;

import com.sprint.hrbank.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface departmentRepository extends JpaRepository<Department, Integer> {
}
