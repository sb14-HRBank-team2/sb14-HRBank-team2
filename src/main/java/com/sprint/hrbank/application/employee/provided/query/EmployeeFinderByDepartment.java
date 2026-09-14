package com.sprint.hrbank.application.employee.provided.query;

import com.sprint.hrbank.domain.employee.Employee;
import java.util.List;

public interface EmployeeFinderByDepartment {

  List<Employee> getByDepartmentId(Integer id);
}
