package com.sprint.hrbank.application.department;

import com.sprint.hrbank.adapter.persistence.department.DepartmentSearchCond;
import com.sprint.hrbank.application.department.dto.CursorPageResponseDepartmentDto;
import com.sprint.hrbank.application.department.dto.DepartmentDto;
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
public class DepartmentQueryService
    implements DepartmentFinder, DepartmentDtoFinder, DepartmentPageMaker {

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
  @Transactional(readOnly = true)
  public DepartmentDto getByDepartmentId(Long departmentId) {
    Department department = getById(departmentId);
    Long employeeCount = finder.countEmployeesByDepartment_Id(departmentId);
    return DepartmentDto.from(department, employeeCount);
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
}
