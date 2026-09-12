package com.sprint.hrbank.application.employee;

import com.sprint.hrbank.application.changelog.ChangeLogService;
import com.sprint.hrbank.application.changelog.dto.ChangeLogCreateRequestDto;
import com.sprint.hrbank.application.changelog.dto.DiffCreateRequestDto;
import com.sprint.hrbank.application.department.provided.DepartmentFinder;
import com.sprint.hrbank.application.employee.dto.EmployeeCreateRequest;
import com.sprint.hrbank.application.employee.dto.EmployeeDto;
import com.sprint.hrbank.application.employee.dto.EmployeeUpdateRequest;
import com.sprint.hrbank.application.employee.provided.command.EmployeeCleaner;
import com.sprint.hrbank.application.employee.provided.command.EmployeeModifier;
import com.sprint.hrbank.application.employee.provided.command.EmployeeRegister;
import com.sprint.hrbank.application.employee.required.EmployeeRepository;
import com.sprint.hrbank.common.exception.CustomRuntimeException;
import com.sprint.hrbank.common.exception.ExceptionType;
import com.sprint.hrbank.domain.chagelog.ChangeType;
import com.sprint.hrbank.domain.department.Department;
import com.sprint.hrbank.domain.employee.Employee;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeCommandService implements EmployeeRegister, EmployeeCleaner, EmployeeModifier {

  private final DepartmentFinder departmentFinder;
  private final EmployeeRepository employeeRepository;
  private final ChangeLogService changeLogService;

  @Override
  @Transactional
  public EmployeeDto register(EmployeeCreateRequest employeeCreateRequest, String ipAddress) {
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
    // 이력생성
    List<DiffCreateRequestDto> diffs =
        List.of(
            new DiffCreateRequestDto("name", "-", employee.getName()),
            new DiffCreateRequestDto("email", "-", employee.getEmail()),
            new DiffCreateRequestDto("department", "-", employee.getDepartment().getName()),
            new DiffCreateRequestDto("position", "-", employee.getPosition()),
            new DiffCreateRequestDto("hireDate", "-", employee.getHireDate().toString()),
            new DiffCreateRequestDto("status", "-", employee.getStatus().name()),
            new DiffCreateRequestDto("employeeNumber", "-", employee.getEmployeeNumber()));
    ChangeLogCreateRequestDto dto =
        new ChangeLogCreateRequestDto(
            ChangeType.CREATED, employee.getEmployeeNumber(), employeeCreateRequest.memo(), diffs);
    changeLogService.create(dto, ipAddress);

    return EmployeeDto.toDto(employee);
  }

  @Override
  @Transactional
  public void deleteById(Integer employeeId, String ipAddress) {
    Employee target =
        employeeRepository
            .findById(employeeId)
            .orElseThrow(() -> new CustomRuntimeException(ExceptionType.USER_NOT_FOUND));
    // 이력생성
    List<DiffCreateRequestDto> diffs =
        List.of(
            new DiffCreateRequestDto("name", target.getName(), "-"),
            new DiffCreateRequestDto("email", target.getEmail(), "-"),
            new DiffCreateRequestDto("department", target.getDepartment().getName(), "-"),
            new DiffCreateRequestDto("position", target.getPosition(), "-"),
            new DiffCreateRequestDto("hireDate", target.getHireDate().toString(), "-"),
            new DiffCreateRequestDto("status", target.getStatus().name(), "-"),
            new DiffCreateRequestDto("employeeNumber", target.getEmployeeNumber(), "-"));
    ChangeLogCreateRequestDto dto =
        new ChangeLogCreateRequestDto(
            ChangeType.DELETED, target.getEmployeeNumber(), "직원 삭제", diffs);
    changeLogService.create(dto, ipAddress);

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
