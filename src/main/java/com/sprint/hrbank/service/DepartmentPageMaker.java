package com.sprint.hrbank.service;

import com.sprint.hrbank.dto.CursorPageResponseDepartmentDto;
import com.sprint.hrbank.repository.DepartmentSearchCond;

public interface DepartmentPageMaker {
  CursorPageResponseDepartmentDto getDepartmentPage(DepartmentSearchCond cond);
}
