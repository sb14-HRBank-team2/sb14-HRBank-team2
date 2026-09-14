package com.sprint.hrbank.application.changelog.dto;

public record DiffCreateRequestDto(String propertyName, String before, String after) {}
