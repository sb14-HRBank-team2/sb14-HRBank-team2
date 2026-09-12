package com.sprint.hrbank.service.command;

import com.sprint.hrbank.dto.EmployeeCreateRequest;
import com.sprint.hrbank.dto.EmployeeDto;
import com.sprint.hrbank.dto.EmployeeUpdateRequest;
import com.sprint.hrbank.entity.Department;
import com.sprint.hrbank.entity.Employee;
import com.sprint.hrbank.exception.CustomRuntimeException;
import com.sprint.hrbank.exception.ExceptionType;
import com.sprint.hrbank.repository.EmployeeRepository;
import com.sprint.hrbank.service.DepartmentFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeCommandService implements EmployeeRegister, EmployeeCleaner, EmployeeModifier {

  private final DepartmentFinder departmentFinder;
  private final EmployeeRepository employeeRepository;

  @Override
  @Transactional
  public EmployeeDto register(EmployeeCreateRequest employeeCreateRequest) {
    Department department = departmentFinder.getById(employeeCreateRequest.departmentId());
    Employee employee =
        Employee.create(
            department,
            null,
            employeeCreateRequest.name(),
            employeeCreateRequest.email(),
            employeeCreateRequest.position(),
            employeeCreateRequest.hireDate());
    employeeRepository.save(employee);
    return EmployeeDto.toDto(employee);
  }

  @Override
  @Transactional
  public void deleteById(Integer employeeId) {
    employeeRepository.deleteById(employeeId);
  }

  @Override
  @Transactional
  public EmployeeDto update(Integer employeeId, EmployeeUpdateRequest request) {
    Employee employee =
        employeeRepository
            .findById(employeeId)
            .orElseThrow(() -> new CustomRuntimeException(ExceptionType.USER_NOT_FOUND));
    Department department = departmentFinder.getById(request.departmentId());
    Employee update =
        employee.update(
            request.name(),
            request.email(),
            department,
            request.position(),
            request.hireDate(),
            request.status());
    return EmployeeDto.toDto(update);
  }
}
