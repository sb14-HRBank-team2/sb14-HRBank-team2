package com.sprint.hrbank.application.employee.required;

import com.sprint.hrbank.adapter.persistence.employee.EmployeeQRepository;
import com.sprint.hrbank.domain.employee.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>, EmployeeQRepository {

  List<Employee> findByNameContainingOrEmailContaining(String nameKeyword, String emailKeyword);

  Integer countEmployeesByDepartment_Id(Integer departmentId);
}
