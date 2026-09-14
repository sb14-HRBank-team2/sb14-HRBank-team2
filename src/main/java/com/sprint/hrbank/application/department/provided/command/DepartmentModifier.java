package com.sprint.hrbank.application.department.provided.command;

import com.sprint.hrbank.application.department.dto.DepartmentDto;
import com.sprint.hrbank.application.department.dto.DepartmentUpdateRequest;

public interface DepartmentModifier {
  DepartmentDto update(Integer departmentId, DepartmentUpdateRequest updateRequest);
}
