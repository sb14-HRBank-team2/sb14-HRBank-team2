package com.sprint.hrbank.application.changelog.provided.query;

import com.sprint.hrbank.application.changelog.dto.ChangeLogDetailDto;

public interface ChangeLogDetailFinder {

  ChangeLogDetailDto getChangeLogDetail(Long changeLogId);
}
