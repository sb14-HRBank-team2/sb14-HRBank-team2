package com.sprint.hrbank.application.employee;

import com.sprint.hrbank.application.employee.dto.EmployeeCreateRequest;
import com.sprint.hrbank.application.employee.required.EmployeeRepository;
import com.sprint.hrbank.domain.department.Department;
import com.sprint.hrbank.domain.employee.Employee;
import com.sprint.hrbank.domain.employee.EmployeeStatus;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeCommandService {

  private final EmployeeRepository employeeRepository;

  public Employee create(
      EmployeeCreateRequest request, Department department, Long profileImageID) {
    Employee employee =
        Employee.create(
            department,
            profileImageID,
            request.name(),
            request.email(),
            request.position(),
            request.hireDate());
    return employeeRepository.save(employee);
  }

  public void delete(Employee employee) {
    employeeRepository.delete(employee);
  }

  public Employee update(
      Employee employee,
      String name,
      String email,
      Department department,
      String positon,
      LocalDate hireDate,
      Long profileImageId,
      EmployeeStatus status) {
    return employee.update(name, email, department, positon, hireDate, profileImageId, status);
  }
}
