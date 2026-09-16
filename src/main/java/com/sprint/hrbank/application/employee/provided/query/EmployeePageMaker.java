package com.sprint.hrbank.application.employee.provided.query;

import com.sprint.hrbank.application.employee.dto.CursorPageResponseEmployeeDto;

public interface EmployeePageMaker {

  CursorPageResponseEmployeeDto getEmployeePage(EmployeeSearchCond cond);
}
