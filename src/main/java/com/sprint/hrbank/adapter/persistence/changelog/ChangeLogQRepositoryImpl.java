package com.sprint.hrbank.adapter.persistence.changelog;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sprint.hrbank.domain.chagelog.ChangeLog;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChangeLogQRepositoryImpl implements ChangeLogQRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<ChangeLog> search(ChangeLogSearchCond cond) {
    return null;
  }
}
