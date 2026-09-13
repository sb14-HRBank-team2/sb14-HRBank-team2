package com.sprint.hrbank.repository;

import com.sprint.hrbank.entity.Department;
import java.util.List;

public interface DepartmentQRepository {
  List<Department> search(DepartmentSearchCond cond);

  Long countByCondition(DepartmentSearchCond cond);
}
