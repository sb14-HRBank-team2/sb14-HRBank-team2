package com.sprint.hrbank.application.changelog.dto;

import com.sprint.hrbank.domain.chagelog.ChangeType;
import java.util.List;

public record ChangeLogCreateRequestDto(
    ChangeType type, String employeeNumber, String memo, List<DiffCreateRequestDto> diffs) {}
