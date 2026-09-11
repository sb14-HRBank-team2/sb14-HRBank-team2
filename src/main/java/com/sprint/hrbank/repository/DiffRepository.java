package com.sprint.hrbank.repository;

import com.sprint.hrbank.entity.Diff;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiffRepository extends JpaRepository<Diff, Integer> {

  List<Diff> findAllByChangeLogId(Integer changeLogId);
}
