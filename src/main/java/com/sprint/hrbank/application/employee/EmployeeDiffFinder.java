package com.sprint.hrbank.application.employee;

import com.sprint.hrbank.application.changelog.dto.DiffCreateRequestDto;
import com.sprint.hrbank.application.employee.dto.EmployeeUpdateRequest;
import com.sprint.hrbank.domain.department.Department;
import com.sprint.hrbank.domain.employee.Employee;
import java.util.ArrayList;
import java.util.List;

public final class EmployeeDiffFinder {

  public static List<DiffCreateRequestDto> createUpdateDiffs(
      Employee employee, EmployeeUpdateRequest request, Department department) {
    List<DiffCreateRequestDto> diffs = new ArrayList<>();

    if (request.name() != null) {
      addDiff(diffs, "name", employee.getName(), request.name());
    }

    if (request.email() != null) {
      addDiff(diffs, "email", employee.getEmail(), request.email());
    }

    if (request.departmentId() != null) {
      addDiff(diffs, "department", employee.getDepartment().getName(), department.getName());
    }

    if (request.position() != null) {
      addDiff(diffs, "position", employee.getPosition(), request.position());
    }

    if (request.hireDate() != null) {
      addDiff(diffs, "hireDate", employee.getHireDate().toString(), request.hireDate().toString());
    }

    if (request.status() != null) {
      addDiff(diffs, "status", employee.getStatus().name(), request.status().name());
    }

    return diffs;
  }

  private static void addDiff(
      List<DiffCreateRequestDto> diffs, String propertyName, String before, String after) {
    if (before.equals(after)) {
      return;
    }

    diffs.add(new DiffCreateRequestDto(propertyName, before, after));
  }
}
