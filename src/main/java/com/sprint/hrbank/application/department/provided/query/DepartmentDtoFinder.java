package com.sprint.hrbank.application.department.provided.query;

import com.sprint.hrbank.application.department.dto.DepartmentDto;

public interface DepartmentDtoFinder {

  DepartmentDto getByDepartmentId(Long departmentId);
}
