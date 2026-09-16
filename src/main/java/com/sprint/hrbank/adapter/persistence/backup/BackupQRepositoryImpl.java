package com.sprint.hrbank.adapter.persistence.backup;

import static com.sprint.hrbank.domain.backup.QBackup.backup;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sprint.hrbank.domain.backup.Backup;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BackupQRepositoryImpl implements BackupQRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Backup> search(BackupSearchCond cond) {
    BooleanBuilder booleanBuilder = builder(cond);
    cursorByCond(booleanBuilder, cond);

    JPAQuery<Backup> jpaQuery = jpaQueryFactory.selectFrom(backup).where(booleanBuilder);

    sortByCond(jpaQuery, cond);
    jpaQuery.limit(cond.size() + 1);

    return jpaQuery.fetch();
  }

  @Override
  public Long countByCondition(BackupSearchCond cond) {
    BooleanBuilder booleanBuilder = builder(cond);
    Long totalElements =
        jpaQueryFactory.select(backup.count()).from(backup).where(booleanBuilder).fetchOne();

    return totalElements == null ? 0L : totalElements;
  }

  private void cursorByCond(BooleanBuilder booleanBuilder, BackupSearchCond cond) {
    if (!hasText(cond.cursor()) && cond.idAfter() == null) {
      return;
    }

    Long idAfter = cond.idAfter();
    LocalDateTime cursorDate = hasText(cond.cursor()) ? LocalDateTime.parse(cond.cursor()) : null;

    if ("startedAt".equals(cond.sortField()) && cursorDate != null) {
      if ("asc".equals(cond.sortDirection())) {
        booleanBuilder.and(
            backup
                .startedAt
                .gt(cursorDate)
                .or(backup.startedAt.eq(cursorDate).and(backup.id.gt(idAfter))));
      } else {
        booleanBuilder.and(
            backup
                .startedAt
                .lt(cursorDate)
                .or(backup.startedAt.eq(cursorDate).and(backup.id.lt(idAfter))));
      }
    }

    if ("endedAt".equals(cond.sortField()) && cursorDate != null) {
      if ("asc".equals(cond.sortDirection())) {
        booleanBuilder.and(
            backup
                .endedAt
                .gt(cursorDate)
                .or(backup.endedAt.eq(cursorDate).and(backup.id.gt(idAfter))));
      } else {
        booleanBuilder.and(
            backup
                .endedAt
                .lt(cursorDate)
                .or(backup.endedAt.eq(cursorDate).and(backup.id.lt(idAfter))));
      }
    }
  }

  private void sortByCond(JPAQuery<Backup> jpaQuery, BackupSearchCond cond) {
    if ("startedAt".equals(cond.sortField())) {
      if (cond.sortDirection().equals("asc")) {
        jpaQuery.orderBy(backup.startedAt.asc(), backup.id.asc());
      } else {
        jpaQuery.orderBy(backup.startedAt.desc(), backup.id.desc());
      }
    }

    if ("endedAt".equals(cond.sortField())) {
      if (cond.sortDirection().equals("asc")) {
        jpaQuery.orderBy(backup.endedAt.asc(), backup.id.asc());
      } else {
        jpaQuery.orderBy(backup.endedAt.desc(), backup.id.desc());
      }
    }
  }

  private BooleanBuilder builder(BackupSearchCond cond) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();

    if (hasText(cond.worker())) {
      booleanBuilder.and(backup.worker.contains(cond.worker()));
    }

    if (cond.status() != null) {
      booleanBuilder.and(backup.status.eq(cond.status()));
    }
    if (cond.startedAtFrom() != null) {
      LocalDateTime startOfDay = cond.startedAtFrom();
      LocalDateTime endOfDay = cond.startedAtTo();

      booleanBuilder.and(backup.startedAt.between(startOfDay, endOfDay));
    }
    return booleanBuilder;
  }
}
