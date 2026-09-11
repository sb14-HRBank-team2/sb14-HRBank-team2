package com.sprint.hrbank.repository;

import com.sprint.hrbank.domain.ChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeLogRepository extends JpaRepository<ChangeLog, Integer> {}
