package com.sprint.hrbank.application.department;

import com.sprint.hrbank.application.department.dto.DepartmentCreateRequest;
import com.sprint.hrbank.application.department.dto.DepartmentDto;
import com.sprint.hrbank.application.department.dto.DepartmentUpdateRequest;
import com.sprint.hrbank.application.department.provided.command.DepartmentCleaner;
import com.sprint.hrbank.application.department.provided.command.DepartmentCreator;
import com.sprint.hrbank.application.department.provided.command.DepartmentModifier;
import com.sprint.hrbank.application.department.required.DepartmentRepository;
import com.sprint.hrbank.application.employee.provided.query.EmployeeCounter;
import com.sprint.hrbank.common.exception.CustomRuntimeException;
import com.sprint.hrbank.common.exception.ExceptionType;
import com.sprint.hrbank.domain.department.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DepartmentCommandService
    implements DepartmentCreator, DepartmentModifier, DepartmentCleaner {

  private final DepartmentRepository departmentRepository;
  private final EmployeeCounter finder;

  @Override
  @Transactional
  public DepartmentDto create(DepartmentCreateRequest request) {
    if (departmentRepository.existsByName(request.name())) {
      throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST);
    }
    Department department =
        Department.create(request.name(), request.description(), request.establishedDate());

    departmentRepository.save(department);

    return DepartmentDto.from(department, 0L);
  }

  @Override
  @Transactional
  public DepartmentDto update(Long departmentId, DepartmentUpdateRequest request) {
    Department department =
        departmentRepository
            .findById(departmentId)
            .orElseThrow(() -> new CustomRuntimeException(ExceptionType.DEPARTMENT_NOT_FOUND));

    if (departmentRepository.existsByNameAndIdNot(request.name(), departmentId)) {
      throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST);
    }

    department.update(request.name(), request.description(), request.establishedDate());
    Long employeeCount = finder.countEmployeesByDepartment_Id(departmentId);

    return DepartmentDto.from(department, employeeCount);
  }

  @Override
  @Transactional
  public void delete(Long departmentId) {
    Department department =
        departmentRepository
            .findById(departmentId)
            .orElseThrow(() -> new CustomRuntimeException(ExceptionType.DEPARTMENT_NOT_FOUND));

    Long employeeCount = finder.countEmployeesByDepartment_Id(departmentId);
    if (employeeCount > 0) {
      throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST);
    }
    departmentRepository.delete(department);
  }
}
