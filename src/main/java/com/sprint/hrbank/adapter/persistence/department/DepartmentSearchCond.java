package com.sprint.hrbank.adapter.persistence.department;

import lombok.Builder;

@Builder
public record DepartmentSearchCond(String nameOrDescription) {}
