package com.sprint.hrbank.service;

import com.sprint.hrbank.entity.Employee;

import java.util.List;

public interface EmployeeFinder {
  Employee getById(Integer employeeId);
  List<Employee> getAll();
}
