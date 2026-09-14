package com.sprint.hrbank.application.facade;

import com.sprint.hrbank.application.changelog.ChangeLogCommandService;
import com.sprint.hrbank.application.changelog.dto.ChangeLogCreateRequestDto;
import com.sprint.hrbank.application.changelog.dto.DiffCreateRequestDto;
import com.sprint.hrbank.application.department.provided.query.DepartmentFinder;
import com.sprint.hrbank.application.employee.EmployeeCommandService;
import com.sprint.hrbank.application.employee.EmployeeDiffFinder;
import com.sprint.hrbank.application.employee.EmployeeQueryService;
import com.sprint.hrbank.application.employee.dto.EmployeeCreateRequest;
import com.sprint.hrbank.application.employee.dto.EmployeeDto;
import com.sprint.hrbank.application.employee.dto.EmployeeUpdateRequest;
import com.sprint.hrbank.application.employee.provided.command.EmployeeCleaner;
import com.sprint.hrbank.application.employee.provided.command.EmployeeModifier;
import com.sprint.hrbank.application.employee.provided.command.EmployeeRegister;
import com.sprint.hrbank.domain.chagelog.ChangeType;
import com.sprint.hrbank.domain.department.Department;
import com.sprint.hrbank.domain.employee.Employee;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class EmployeeAppService implements EmployeeRegister, EmployeeCleaner, EmployeeModifier {

  private final DepartmentFinder departmentFinder;
  private final EmployeeQueryService employeeQueryService;
  //  private final FileService fileService;
  private final EmployeeCommandService employeeCommandService;
  private final ChangeLogCommandService changeLogCommandService;

  @Override
  @Transactional
  public EmployeeDto register(
      EmployeeCreateRequest employeeCreateRequest, String ipAddress, MultipartFile profile) {
    Department department = departmentFinder.getById(employeeCreateRequest.departmentId());
    Long profileImageId = null;
    if (profile != null && !profile.isEmpty()) {
      // profileImageId = fileService.uploadFile(profile);
    }

    Employee employee =
        employeeCommandService.create(employeeCreateRequest, department, profileImageId);
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

    changeLogCommandService.create(dto, ipAddress);

    return EmployeeDto.from(employee);
  }

  @Override
  @Transactional
  public void deleteById(Long employeeId, String ipAddress) {

    Employee target = employeeQueryService.getEmployee(employeeId);
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
    changeLogCommandService.create(dto, ipAddress);
    if (target.getProfileImageId() != null) {
      // fileService.delete(profileImageId);
    }
    employeeCommandService.delete(target);
  }

  @Override
  @Transactional
  public EmployeeDto update(
      Long employeeId, EmployeeUpdateRequest request, String ipAddress, MultipartFile profile) {

    Long profileImageId = null;
    if (profile != null && !profile.isEmpty()) {
      // profileImageId = fileService.uploadFile(profile);
    }

    Employee employee = employeeQueryService.getEmployee(employeeId);

    Department department = employee.getDepartment();
    if (request.departmentId() != null) {
      department = departmentFinder.getById(request.departmentId());
    }

    List<DiffCreateRequestDto> diffs =
        EmployeeDiffFinder.createUpdateDiffs(employee, request, department);

    Employee update =
        employeeCommandService.update(
            employee,
            request.name(),
            request.email(),
            department,
            request.position(),
            request.hireDate(),
            profileImageId,
            request.status());

    ChangeLogCreateRequestDto dto =
        new ChangeLogCreateRequestDto(
            ChangeType.UPDATED, employee.getEmployeeNumber(), request.memo(), diffs);

    changeLogCommandService.create(dto, ipAddress);

    return EmployeeDto.from(update);
  }
}
