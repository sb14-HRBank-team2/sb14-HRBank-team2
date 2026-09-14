package com.sprint.hrbank.application.employee.provided.command;

public interface EmployeeCleaner {

  void deleteById(Long employeeId, String ipAddress);
}
