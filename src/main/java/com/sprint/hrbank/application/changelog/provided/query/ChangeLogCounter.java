package com.sprint.hrbank.application.changelog.provided.query;

import java.time.LocalDateTime;

public interface ChangeLogCounter {

  Long getChangeLogCount(LocalDateTime fromDate, LocalDateTime toDate);
}
