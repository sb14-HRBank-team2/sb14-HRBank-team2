package com.sprint.hrbank.repository;

import com.sprint.hrbank.entity.Employee;
import java.util.List;

public interface EmployeeQRepository {

  List<Employee> search(EmployeeSearchCond employeeSearchCond);
}
