package com.sprint.hrbank.adapter.persistence.department;

import com.sprint.hrbank.domain.department.Department;
import java.util.List;

public interface DepartmentQRepository {

  List<Department> search(DepartmentSearchCond cond);
}
