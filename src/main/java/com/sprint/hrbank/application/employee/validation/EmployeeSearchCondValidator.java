package com.sprint.hrbank.application.employee.validation;

import com.sprint.hrbank.adapter.persistence.employee.EmployeeSearchCond;
import com.sprint.hrbank.common.exception.CustomRuntimeException;
import com.sprint.hrbank.common.exception.ExceptionType;

public final class EmployeeSearchCondValidator {

  private EmployeeSearchCondValidator() {}

  public static void validateEmployeeSearchCond(EmployeeSearchCond cond) {
    validateSize(cond);
    validateSortField(cond);
    validateSortDirection(cond);
  }

  private static void validateSortField(EmployeeSearchCond cond) {
    if (!("name".equals(cond.sortField()))
        && !("hireDate".equals(cond.sortField()))
        && !("employeeNumber".equals(cond.sortField()))) {
      validateFail();
    }
  }

  private static void validateSortDirection(EmployeeSearchCond cond) {
    if (!("asc".equals(cond.sortDirection())) && !("desc".equals(cond.sortDirection()))) {
      validateFail();
    }
  }

  private static void validateSize(EmployeeSearchCond cond) {
    if (cond.size() <= 0) {
      validateFail();
    }
  }

  private static void validateFail() {
    throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST);
  }
}
