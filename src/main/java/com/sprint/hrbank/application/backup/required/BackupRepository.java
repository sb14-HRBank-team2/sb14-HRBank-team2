package com.sprint.hrbank.application.backup.required;

import com.sprint.hrbank.domain.backup.Backup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackupRepository extends JpaRepository<Backup, Long> {}
