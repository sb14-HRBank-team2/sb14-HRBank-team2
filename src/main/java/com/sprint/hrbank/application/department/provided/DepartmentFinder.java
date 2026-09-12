package com.sprint.hrbank.application.department.provided;

import com.sprint.hrbank.domain.department.Department;

public interface DepartmentFinder {

  Department getById(Integer departmentId);
}
