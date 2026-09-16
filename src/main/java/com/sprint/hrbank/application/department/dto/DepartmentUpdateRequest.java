package com.sprint.hrbank.application.department.dto;

import java.time.LocalDate;

public record DepartmentUpdateRequest(String name, String description, LocalDate establishedDate) {}
