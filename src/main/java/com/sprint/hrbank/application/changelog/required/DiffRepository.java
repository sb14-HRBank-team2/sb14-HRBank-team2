package com.sprint.hrbank.application.changelog.required;

import com.sprint.hrbank.domain.chagelog.Diff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiffRepository extends JpaRepository<Diff, Integer> {}
