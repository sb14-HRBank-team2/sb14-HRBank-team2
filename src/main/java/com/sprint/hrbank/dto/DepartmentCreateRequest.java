package com.sprint.hrbank.dto;

import java.time.LocalDate;

public record DepartmentCreateRequest(String name, String description, LocalDate establishedDate) {}
