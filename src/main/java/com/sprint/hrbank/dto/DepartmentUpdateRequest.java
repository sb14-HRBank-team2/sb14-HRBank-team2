package com.sprint.hrbank.dto;

import java.time.LocalDate;

public record DepartmentUpdateRequest(String name, String description, LocalDate establishedDate) {}
