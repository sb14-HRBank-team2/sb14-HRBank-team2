package com.sprint.hrbank.application.employee;

import static com.sprint.hrbank.application.employee.validation.EmployeeSearchCondValidator.validateEmployeeSearchCond;

import com.sprint.hrbank.application.employee.dto.CursorPageResponseEmployeeDto;
import com.sprint.hrbank.application.employee.dto.EmployeeDto;
import com.sprint.hrbank.application.employee.provided.query.EmployeeCounter;
import com.sprint.hrbank.application.employee.provided.query.EmployeeFinder;
import com.sprint.hrbank.application.employee.provided.query.EmployeePageMaker;
import com.sprint.hrbank.application.employee.provided.query.EmployeeSearchCond;
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
public class EmployeeQueryService implements EmployeeFinder, EmployeePageMaker, EmployeeCounter {

  private final EmployeeRepository employeeRepository;

  @Override
  @Transactional(readOnly = true)
  public EmployeeDto getById(Long employeeId) {
    Employee employee =
        employeeRepository
            .findById(employeeId)
            .orElseThrow(() -> new CustomRuntimeException(ExceptionType.EMPLOYEE_NOT_FOUND));
    return EmployeeDto.from(employee);
  }

  @Transactional(readOnly = true)
  @Override
  public CursorPageResponseEmployeeDto getEmployeePage(EmployeeSearchCond cond) {
    validateEmployeeSearchCond(cond);
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
    List<EmployeeDto> pagedDto = paged.stream().map(EmployeeDto::from).toList();
    String nextCursor = null;
    Long nextIdAfter = null;
    Long totalElements = employeeRepository.countByCondition(cond);
    if (hasNext && !paged.isEmpty()) {
      Employee employee = paged.get(paged.size() - 1);
      String sortField = cond.sortField();
      nextCursor = getCursor(employee, sortField);
      nextIdAfter = employee.getId();
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

  //  sortField 타입 확인 후 타입에 맞는 값 반환
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

  @Override
  public Long countEmployeesByDepartment_Id(Long departmentId) {
    return employeeRepository.countEmployeesByDepartment_Id(departmentId);
  }
}
