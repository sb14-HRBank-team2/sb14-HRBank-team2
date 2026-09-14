package com.sprint.hrbank.application.department.dto;

import java.time.LocalDate;

public record DepartmentCreateRequest(String name, String description, LocalDate establishedDate) {}
