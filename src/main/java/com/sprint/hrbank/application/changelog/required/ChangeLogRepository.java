package com.sprint.hrbank.application.changelog.required;

import com.sprint.hrbank.domain.chagelog.ChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangeLogRepository extends JpaRepository<ChangeLog, Integer> {}
