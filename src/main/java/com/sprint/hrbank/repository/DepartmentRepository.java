package com.sprint.hrbank.repository;

import com.sprint.hrbank.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

interface DepartmentRepository extends JpaRepository<Department, Long> {

}
