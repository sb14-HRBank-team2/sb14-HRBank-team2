package com.sprint.hrbank.service;

import com.sprint.hrbank.entity.Employee;
import java.util.List;

public interface EmployeeFinderByDepartment {

  List<Employee> getByDepartmentId(Integer id);
}
