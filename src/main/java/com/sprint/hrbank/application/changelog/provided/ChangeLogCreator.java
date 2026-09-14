package com.sprint.hrbank.application.changelog.provided;

import com.sprint.hrbank.application.changelog.dto.ChangeLogCreateRequestDto;
import com.sprint.hrbank.application.changelog.dto.ChangeLogResponseDto;

public interface ChangeLogCreator {
  ChangeLogResponseDto create(ChangeLogCreateRequestDto dto, String ipAddress);
}
