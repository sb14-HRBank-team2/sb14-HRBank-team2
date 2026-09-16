package com.sprint.hrbank.adapter.persistence.backup;

import com.sprint.hrbank.domain.backup.Backup;
import java.util.List;

public interface BackupQRepository {

  List<Backup> search(BackupSearchCond cond);

  Long countByCondition(BackupSearchCond cond);
}
