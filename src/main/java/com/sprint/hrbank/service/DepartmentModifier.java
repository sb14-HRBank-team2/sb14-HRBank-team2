package com.sprint.hrbank.service;

import com.sprint.hrbank.dto.DepartmentDto;
import com.sprint.hrbank.dto.DepartmentUpdateRequest;

public interface DepartmentModifier {
  DepartmentDto update(Integer departmentId, DepartmentUpdateRequest updateRequest);
}
