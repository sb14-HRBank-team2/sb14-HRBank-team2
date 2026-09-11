package com.sprint.hrbank.service;

import com.sprint.hrbank.domain.Employee;
import com.sprint.hrbank.dto.EmployeeDto;
import com.sprint.hrbank.exception.CustomRuntimeException;
import com.sprint.hrbank.exception.ExceptionType;
import com.sprint.hrbank.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
  private final EmployeeRepository employeeRepository;

  @Transactional(readOnly = true)
  public EmployeeDto getEmployeeDetail(Integer id) {
    Employee employee =
        employeeRepository
            .findById(id)
            .orElseThrow(() -> new CustomRuntimeException(ExceptionType.USER_NOT_FOUND, id));
    return EmployeeDto.from(employee);
  }
}
