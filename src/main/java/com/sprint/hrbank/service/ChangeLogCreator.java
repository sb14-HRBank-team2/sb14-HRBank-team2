package com.sprint.hrbank.service;

import com.sprint.hrbank.dto.ChangeLogCreateRequestDto;
import com.sprint.hrbank.dto.ChangeLogResponseDto;

public interface ChangeLogCreator {
  ChangeLogResponseDto create(ChangeLogCreateRequestDto dto, String ipAddress);
}
