package com.sprint.hrbank.application.employee.provided.query;

import com.sprint.hrbank.domain.employee.Employee;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface EmployeeEntityFinder {

  Employee getEmployee(Long id);

  List<Employee> getAll(Sort sort);
}
