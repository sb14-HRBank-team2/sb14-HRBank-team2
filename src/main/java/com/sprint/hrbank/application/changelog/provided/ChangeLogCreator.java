package com.sprint.hrbank.application.changelog.provided;

import com.sprint.hrbank.application.changelog.dto.ChangeLogCreateRequestDto;
import com.sprint.hrbank.application.changelog.dto.ChangeLogDto;

public interface ChangeLogCreator {

  ChangeLogDto create(ChangeLogCreateRequestDto dto, String ipAddress);
}
