package com.sprint.hrbank.application.employee;

import com.sprint.hrbank.adapter.persistence.employee.EmployeeSearchCond;
import com.sprint.hrbank.application.department.provided.query.DepartmentFinder;
import com.sprint.hrbank.application.employee.dto.EmployeeCreateRequest;
import com.sprint.hrbank.application.employee.dto.EmployeeDto;
import com.sprint.hrbank.application.employee.dto.EmployeeUpdateRequest;
import com.sprint.hrbank.application.employee.provided.command.EmployeeCleaner;
import com.sprint.hrbank.application.employee.provided.command.EmployeeModifier;
import com.sprint.hrbank.application.employee.provided.command.EmployeeRegister;
import com.sprint.hrbank.application.employee.provided.query.EmployeeFinder;
import com.sprint.hrbank.application.employee.provided.query.EmployeeFinderByDepartment;
import com.sprint.hrbank.application.employee.required.EmployeeRepository;
import com.sprint.hrbank.common.exception.CustomRuntimeException;
import com.sprint.hrbank.common.exception.ExceptionType;
import com.sprint.hrbank.domain.department.Department;
import com.sprint.hrbank.domain.employee.Employee;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EmployeeService
    implements EmployeeRegister,
        EmployeeCleaner,
        EmployeeFinder,
        EmployeeModifier,
        EmployeeFinderByDepartment {

  private final EmployeeRepository employeeRepository;
  private final DepartmentFinder departmentFinder;

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
  @Transactional(readOnly = true)
  public Employee getById(Integer employeeId) {
    return employeeRepository
        .findById(employeeId)
        .orElseThrow(() -> new CustomRuntimeException(ExceptionType.USER_NOT_FOUND));
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

  @Override
  public List<Employee> getByDepartmentId(Integer id) {
    String departmentName = departmentFinder.getById(id).getName();
    EmployeeSearchCond cond = EmployeeSearchCond.builder().departmentName(departmentName).build();
    List<Employee> employees = employeeRepository.search(cond);
    return employees;
  }
}
