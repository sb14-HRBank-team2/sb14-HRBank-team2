package com.sprint.hrbank.repository;

import com.sprint.hrbank.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
  boolean existsByName(String name);

  boolean existsByNameAndIdNot(String name, Integer id);
}
