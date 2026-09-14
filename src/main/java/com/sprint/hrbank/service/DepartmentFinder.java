package com.sprint.hrbank.service;

import com.sprint.hrbank.entity.Department;

public interface DepartmentFinder {

  Department getById(Integer departmentId);
}
