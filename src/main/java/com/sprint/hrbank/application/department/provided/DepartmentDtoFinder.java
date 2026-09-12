package com.sprint.hrbank.application.department.provided;

import com.sprint.hrbank.application.department.dto.DepartmentDto;

public interface DepartmentDtoFinder {

  DepartmentDto getByDepartmentId(Integer departmentId);
}
