package com.sprint.hrbank.service;

import com.sprint.hrbank.dto.DepartmentCreateRequest;
import com.sprint.hrbank.dto.DepartmentDto;
import com.sprint.hrbank.dto.DepartmentUpdateRequest;
import com.sprint.hrbank.entity.Department;
import com.sprint.hrbank.exception.CustomRuntimeException;
import com.sprint.hrbank.exception.ExceptionType;
import com.sprint.hrbank.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DepartmentService
    implements DepartmentFinder,
        DepartmentDtoFinder,
        DepartmentCreator,
        DepartmentModifier,
        DepartmentCleaner {

  private final DepartmentRepository departmentRepository;
  private final EmployeeFinderByDepartment finder;

  @Override
  @Transactional(readOnly = true)
  public DepartmentDto getByDepartmentId(Integer departmentId) {
    Department department =
        departmentRepository
            .findById(departmentId)
            .orElseThrow(() -> new CustomRuntimeException(ExceptionType.DEPARTMENT_NOT_FOUND));
    Integer employeeCount = finder.countByDepartmentId(departmentId);

    return DepartmentDto.from(department, employeeCount);
  }

  @Override
  @Transactional(readOnly = true)
  public Department getById(Integer departmentId) {
    return departmentRepository
        .findById(departmentId)
        .orElseThrow(() -> new CustomRuntimeException(ExceptionType.DEPARTMENT_NOT_FOUND));
  }

  @Override
  @Transactional
  public DepartmentDto create(DepartmentCreateRequest request) {
    if (departmentRepository.existsByName(request.name())) {
      throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST, "이미 존재하는 부서 이름입니다.");
    }
    Department department =
        Department.create(request.name(), request.description(), request.establishedDate());

    departmentRepository.save(department);

    return DepartmentDto.from(department, 0);
  }

  @Override
  @Transactional
  public DepartmentDto update(Integer departmentId, DepartmentUpdateRequest request) {
    Department department =
        departmentRepository
            .findById(departmentId)
            .orElseThrow(() -> new CustomRuntimeException(ExceptionType.DEPARTMENT_NOT_FOUND));

    if (departmentRepository.existsByNameAndIdNot(request.name(), departmentId)) {
      throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST, "이미 존재하는 부서 이름입니다.");
    }

    department.update(request.name(), request.description(), request.establishedDate());
    Integer employeeCount = finder.countByDepartmentId(departmentId);

    return DepartmentDto.from(department, employeeCount);
  }

  @Override
  @Transactional
  public void delete(Integer departmentId) {
    Department department =
        departmentRepository
            .findById(departmentId)
            .orElseThrow(() -> new CustomRuntimeException(ExceptionType.DEPARTMENT_NOT_FOUND));

    Integer employeeCount = finder.countByDepartmentId(departmentId);
    if (employeeCount > 0) {
      throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST, "소속된 직원이 있어 부서를 삭제할 수 없습니다.");
    }
    departmentRepository.delete(department);
  }
}
