package com.sprint.hrbank.adapter.persistence.changelog;

import com.sprint.hrbank.domain.chagelog.ChangeLog;
import java.util.List;

public interface ChangeLogQRepository {

  List<ChangeLog> search(ChangeLogSearchCond cond);
}
