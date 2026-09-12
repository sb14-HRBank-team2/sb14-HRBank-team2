package com.sprint.hrbank.application.department.required;

import com.sprint.hrbank.adapter.persistence.department.DepartmentQRepository;
import com.sprint.hrbank.domain.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository
    extends JpaRepository<Department, Integer>, DepartmentQRepository {}
