package com.sprint.hrbank.application.employee;

import com.sprint.hrbank.adapter.persistence.employee.EmployeeSearchCond;
import com.sprint.hrbank.application.department.provided.DepartmentFinder;
import com.sprint.hrbank.application.employee.dto.EmployeeDto;
import com.sprint.hrbank.application.employee.provided.query.EmployeeFinder;
import com.sprint.hrbank.application.employee.provided.query.EmployeeFinderByDepartment;
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
