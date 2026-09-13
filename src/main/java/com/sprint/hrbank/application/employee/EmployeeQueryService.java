package com.sprint.hrbank.application.employee;

import com.sprint.hrbank.adapter.persistence.employee.EmployeeSearchCond;
import com.sprint.hrbank.application.department.provided.DepartmentFinder;
import com.sprint.hrbank.application.employee.dto.CursorPageResponseEmployeeDto;
import com.sprint.hrbank.application.employee.dto.EmployeeDto;
import com.sprint.hrbank.application.employee.provided.query.EmployeeFinder;
import com.sprint.hrbank.application.employee.provided.query.EmployeeFinderByDepartment;
import com.sprint.hrbank.application.employee.provided.query.EmployeePageMaker;
import com.sprint.hrbank.application.employee.required.EmployeeRepository;
import com.sprint.hrbank.common.exception.CustomRuntimeException;
import com.sprint.hrbank.common.exception.ExceptionType;
import com.sprint.hrbank.domain.employee.Employee;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EmployeeQueryService
    implements EmployeeFinder, EmployeeFinderByDepartment, EmployeePageMaker {

  private final EmployeeRepository employeeRepository;
  private final DepartmentFinder departmentFinder;

  @Override
  @Transactional(readOnly = true)
  public EmployeeDto getById(Integer employeeId) {
    Employee employee =
        employeeRepository
            .findById(employeeId)
            .orElseThrow(() -> new CustomRuntimeException(ExceptionType.USER_NOT_FOUND));
    return EmployeeDto.toDto(employee);
  }

  @Override
  public List<Employee> getByDepartmentId(Integer id) {
    String departmentName = departmentFinder.getById(id).getName();
    EmployeeSearchCond cond = EmployeeSearchCond.builder().departmentName(departmentName).build();
    List<Employee> employees = employeeRepository.search(cond);
    return employees;
  }

  @Transactional(readOnly = true)
  @Override
  public CursorPageResponseEmployeeDto getEmployeePage(EmployeeSearchCond cond) {
    List<Employee> searched = employeeRepository.search(cond);
    Integer size = cond.size();
    Integer adjustSize = searched.size();
    boolean hasNext = adjustSize > size;
    List<Employee> paged;
    if (hasNext) {
      paged = searched.subList(0, size);
    } else {
      paged = searched;
    }
    List<EmployeeDto> pagedDto = paged.stream().map(EmployeeDto::toDto).toList();
    String nextCursor = null;
    Long nextIdAfter = null;
    Long totalElements = employeeRepository.countByCondition(cond);
    if (hasNext && !paged.isEmpty()) {
      Employee employee = paged.get(paged.size() - 1);
      String sortField = cond.sortField();
      nextCursor = getCursor(employee, sortField);
      nextIdAfter = Long.valueOf(employee.getId());
    }
    return CursorPageResponseEmployeeDto.builder()
        .content(pagedDto)
        .nextCursor(nextCursor)
        .nextIdAfter(nextIdAfter)
        .size(size)
        .totalElements(totalElements)
        .hasNext(hasNext)
        .build();
  }

  private String getCursor(Employee employee, String sortField) {
    if ("name".equals(sortField)) {
      return employee.getName();
    }
    if ("employeeNumber".equals(sortField)) {
      return employee.getEmployeeNumber();
    }
    if ("hireDate".equals(sortField)) {
      return String.valueOf(employee.getHireDate());
    }
    throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST);
  }
}
