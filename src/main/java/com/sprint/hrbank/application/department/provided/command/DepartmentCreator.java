package com.sprint.hrbank.application.department.provided.command;

import com.sprint.hrbank.application.department.dto.DepartmentCreateRequest;
import com.sprint.hrbank.application.department.dto.DepartmentDto;

public interface DepartmentCreator {
  DepartmentDto create(DepartmentCreateRequest createRequest);
}
