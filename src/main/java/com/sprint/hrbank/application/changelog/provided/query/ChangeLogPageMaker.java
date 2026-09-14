package com.sprint.hrbank.application.changelog.provided.query;

import com.sprint.hrbank.adapter.persistence.changelog.ChangeLogSearchCond;
import com.sprint.hrbank.application.changelog.dto.CursorPageResponseChangeLogDto;

public interface ChangeLogPageMaker {

  CursorPageResponseChangeLogDto getChangeLogPage(ChangeLogSearchCond cond);
}
