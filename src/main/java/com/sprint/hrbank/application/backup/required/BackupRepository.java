package com.sprint.hrbank.application.backup.required;

import com.sprint.hrbank.adapter.persistence.backup.BackupQRepository;
import com.sprint.hrbank.domain.backup.Backup;
import com.sprint.hrbank.domain.backup.BackupStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackupRepository extends JpaRepository<Backup, Long>, BackupQRepository {

  boolean existsByStatus(BackupStatus status);

  Optional<Backup> findTopByStatusOrderByEndedAtDesc(BackupStatus status);
}
