package com.sprint.hrbank.repository;

import com.sprint.hrbank.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {}
