package com.sprint.hrbank.service;

import com.sprint.hrbank.dto.DepartmentDto;

public interface DepartmentDtoFinder {

  DepartmentDto getByDepartmentId(Integer departmentId);
}
