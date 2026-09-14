package com.sprint.hrbank.application.department.provided.query;

import com.sprint.hrbank.domain.department.Department;

public interface DepartmentFinder {

  Department getById(Integer departmentId);
}
