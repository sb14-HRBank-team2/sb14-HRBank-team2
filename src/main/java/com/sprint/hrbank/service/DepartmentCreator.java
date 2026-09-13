package com.sprint.hrbank.service;

import com.sprint.hrbank.dto.DepartmentCreateRequest;
import com.sprint.hrbank.dto.DepartmentDto;

public interface DepartmentCreator {
  DepartmentDto create(DepartmentCreateRequest createRequest);
}
