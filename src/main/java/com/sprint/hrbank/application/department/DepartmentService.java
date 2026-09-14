package com.sprint.hrbank.application.department;

import com.sprint.hrbank.adapter.persistence.department.DepartmentSearchCond;
import com.sprint.hrbank.application.department.dto.CursorPageResponseDepartmentDto;
import com.sprint.hrbank.application.department.dto.DepartmentCreateRequest;
import com.sprint.hrbank.application.department.dto.DepartmentDto;
import com.sprint.hrbank.application.department.dto.DepartmentUpdateRequest;
import com.sprint.hrbank.application.department.provided.command.DepartmentCleaner;
import com.sprint.hrbank.application.department.provided.command.DepartmentCreator;
import com.sprint.hrbank.application.department.provided.command.DepartmentModifier;
import com.sprint.hrbank.application.department.provided.query.DepartmentDtoFinder;
import com.sprint.hrbank.application.department.provided.query.DepartmentFinder;
import com.sprint.hrbank.application.department.provided.query.DepartmentPageMaker;
import com.sprint.hrbank.application.department.required.DepartmentRepository;
import com.sprint.hrbank.application.employee.provided.query.EmployeeCounter;
import com.sprint.hrbank.common.exception.CustomRuntimeException;
import com.sprint.hrbank.common.exception.ExceptionType;
import com.sprint.hrbank.domain.department.Department;
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
  private final EmployeeCounter finder;

  @Override
  @Transactional(readOnly = true)
  public Department getById(Long departmentId) {
    return departmentRepository
        .findById(departmentId)
        .orElseThrow(() -> new CustomRuntimeException(ExceptionType.DEPARTMENT_NOT_FOUND));
  }

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
            .map(
                dept ->
                    DepartmentDto.from(dept, finder.countEmployeesByDepartment_Id(dept.getId())))
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

  @Override
  public DepartmentDto getByDepartmentId(Long departmentId) {
    return null;
  }
}
