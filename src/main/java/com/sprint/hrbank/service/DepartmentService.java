package com.sprint.hrbank.service;

import com.sprint.hrbank.dto.CursorPageResponseDepartmentDto;
import com.sprint.hrbank.dto.DepartmentCreateRequest;
import com.sprint.hrbank.dto.DepartmentDto;
import com.sprint.hrbank.dto.DepartmentUpdateRequest;
import com.sprint.hrbank.entity.Department;
import com.sprint.hrbank.exception.CustomRuntimeException;
import com.sprint.hrbank.exception.ExceptionType;
import com.sprint.hrbank.repository.DepartmentRepository;
import com.sprint.hrbank.repository.DepartmentSearchCond;
import java.util.List;
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
        DepartmentCleaner,
        DepartmentPageMaker {

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

  @Transactional(readOnly = true)
  @Override
  public CursorPageResponseDepartmentDto getDepartmentPage(DepartmentSearchCond cond) {
    List<Department> searched = departmentRepository.search(cond);

    Integer size = cond.size();
    boolean hasNext = searched.size() > size;
    List<Department> paged;

    if (hasNext) {
      paged = searched.subList(0, size);
    } else {
      paged = searched;
    }

    List<DepartmentDto> pagedDto =
        paged.stream()
            .map(dept -> DepartmentDto.from(dept, finder.countByDepartmentId(dept.getId())))
            .toList();

    String nextCursor = null;
    Long nextIdAfter = null;

    Long totalElements = departmentRepository.countByCondition(cond);

    if (hasNext && !paged.isEmpty()) {
      Department lastDepartment = paged.get(paged.size() - 1);
      nextCursor = getCursor(lastDepartment, cond.sortField());
      nextIdAfter = lastDepartment.getId().longValue();
    }
    return CursorPageResponseDepartmentDto.builder()
        .content(pagedDto)
        .nextCursor(nextCursor)
        .nextIdAfter(nextIdAfter)
        .size(size)
        .totalElements(totalElements)
        .hasNext(hasNext)
        .build();
  }

  private String getCursor(Department department, String sortField) {
    if ("name".equals(sortField)) {
      return department.getName();
    }
    if ("establishedDate".equals(sortField)) {
      return String.valueOf(department.getEstablishedDate());
    }
    throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST);
  }
}
