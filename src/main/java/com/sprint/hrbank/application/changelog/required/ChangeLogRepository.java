package com.sprint.hrbank.application.changelog.required;

import com.sprint.hrbank.adapter.persistence.changelog.ChangeLogQRepository;
import com.sprint.hrbank.domain.chagelog.ChangeLog;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangeLogRepository extends JpaRepository<ChangeLog, Long>, ChangeLogQRepository {

  Long countByAtBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
