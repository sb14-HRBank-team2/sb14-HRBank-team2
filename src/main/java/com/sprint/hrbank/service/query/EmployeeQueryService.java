package com.sprint.hrbank.service.query;

import com.sprint.hrbank.dto.EmployeeDto;
import com.sprint.hrbank.entity.Employee;
import com.sprint.hrbank.exception.CustomRuntimeException;
import com.sprint.hrbank.exception.ExceptionType;
import com.sprint.hrbank.repository.EmployeeRepository;
import com.sprint.hrbank.repository.EmployeeSearchCond;
import com.sprint.hrbank.service.DepartmentFinder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EmployeeQueryService implements EmployeeFinder, EmployeeFinderByDepartment {

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
}
