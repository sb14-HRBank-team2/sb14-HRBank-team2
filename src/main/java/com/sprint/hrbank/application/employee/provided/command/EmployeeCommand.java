package com.sprint.hrbank.application.employee.provided.command;

import com.sprint.hrbank.application.employee.dto.EmployeeCreateRequest;
import com.sprint.hrbank.domain.department.Department;
import com.sprint.hrbank.domain.employee.Employee;
import com.sprint.hrbank.domain.employee.EmployeeStatus;
import java.time.LocalDate;

public interface EmployeeCommand {

  Employee create(EmployeeCreateRequest request, Department department, Long profileImageID);

  void delete(Employee employee);

  Employee update(
      Employee employee,
      String name,
      String email,
      Department department,
      String positon,
      LocalDate hireDate,
      Long profileImageId,
      EmployeeStatus status);
}
