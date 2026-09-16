package com.sprint.hrbank.application.changelog.required;

import com.sprint.hrbank.domain.chagelog.Diff;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiffRepository extends JpaRepository<Diff, Long> {

  List<Diff> findAllByChangeLogId(Long changeLogId);
}
