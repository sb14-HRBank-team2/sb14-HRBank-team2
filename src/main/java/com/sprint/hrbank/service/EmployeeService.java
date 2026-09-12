package com.sprint.hrbank.service;

import com.sprint.hrbank.ChangeType;
import com.sprint.hrbank.dto.ChangeLogCreateRequestDto;
import com.sprint.hrbank.dto.DiffCreateRequestDto;
import com.sprint.hrbank.dto.EmployeeCreateRequest;
import com.sprint.hrbank.dto.EmployeeDto;
import com.sprint.hrbank.dto.EmployeeUpdateRequest;
import com.sprint.hrbank.entity.Department;
import com.sprint.hrbank.entity.Employee;
import com.sprint.hrbank.exception.CustomRuntimeException;
import com.sprint.hrbank.exception.ExceptionType;
import com.sprint.hrbank.repository.EmployeeRepository;
import com.sprint.hrbank.repository.EmployeeSearchCond;
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
  private final ChangeLogCreator changeLogCreator;

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
  public void deleteById(Integer employeeId, String ipAddress) {
    Employee employee =
        employeeRepository
            .findById(employeeId)
            .orElseThrow(() -> new CustomRuntimeException(ExceptionType.USER_NOT_FOUND));

    List<DiffCreateRequestDto> diffs =
        List.of(
            new DiffCreateRequestDto("입사일", employee.getHireDate().toString(), null),
            new DiffCreateRequestDto("이름", employee.getName(), null),
            new DiffCreateRequestDto("직함", employee.getPosition(), null),
            new DiffCreateRequestDto("부서", employee.getDepartment().getName(), null),
            new DiffCreateRequestDto("이메일", employee.getEmail(), null),
            new DiffCreateRequestDto("상태", employee.getStatus().getDescription(), null));

    ChangeLogCreateRequestDto logCreateRequestDto =
        ChangeLogCreateRequestDto.builder()
            .type(ChangeType.DELETED)
            .employeeNumber(employee.getEmployeeNumber())
            .memo("관리자에 의한 직원 영구 삭제")
            .diffs(diffs)
            .build();

    changeLogCreator.create(logCreateRequestDto, ipAddress);

    employeeRepository.delete(employee);
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
