package com.sprint.hrbank.application.department.provided.query;

import com.sprint.hrbank.adapter.persistence.department.DepartmentSearchCond;
import com.sprint.hrbank.application.department.dto.CursorPageResponseDepartmentDto;

public interface DepartmentPageMaker {
  CursorPageResponseDepartmentDto getDepartmentPage(DepartmentSearchCond cond);
}
