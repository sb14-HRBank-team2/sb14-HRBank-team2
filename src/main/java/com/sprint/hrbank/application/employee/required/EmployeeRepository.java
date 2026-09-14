package com.sprint.hrbank.application.employee.required;

import com.sprint.hrbank.domain.employee.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeQRepository {

  List<Employee> findByNameContainingOrEmailContaining(String nameKeyword, String emailKeyword);

  Optional<Employee> findByEmployeeNumber(String employeeNumber);

  Integer countEmployeesByDepartment_Id(Long departmentId);
}
