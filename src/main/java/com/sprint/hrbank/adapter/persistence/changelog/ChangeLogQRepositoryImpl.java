package com.sprint.hrbank.adapter.persistence.changelog;

import static com.sprint.hrbank.domain.chagelog.QChangeLog.changeLog;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sprint.hrbank.common.exception.CustomRuntimeException;
import com.sprint.hrbank.common.exception.ExceptionType;
import com.sprint.hrbank.domain.chagelog.ChangeLog;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChangeLogQRepositoryImpl implements ChangeLogQRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<ChangeLog> search(ChangeLogSearchCond cond) {
    BooleanBuilder booleanBuilder = builder(cond);
    cursorByCond(booleanBuilder, cond);

    JPAQuery<ChangeLog> query = jpaQueryFactory.selectFrom(changeLog).where(booleanBuilder);
    sortByCond(query, cond);
    return query.limit(cond.size() + 1).fetch();
  }

  @Override
  public Long countByCondition(ChangeLogSearchCond cond) {
    Long totalElements =
        jpaQueryFactory.select(changeLog.count()).from(changeLog).where(builder(cond)).fetchOne();
    return totalElements == null ? 0L : totalElements;
  }

  private BooleanBuilder builder(ChangeLogSearchCond cond) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    if (hasText(cond.employeeNumber())) {
      booleanBuilder.and(changeLog.employeeNumber.contains(cond.employeeNumber()));
    }
    if (cond.type() != null) {
      booleanBuilder.and(changeLog.type.eq(cond.type()));
    }
    if (hasText(cond.memo())) {
      booleanBuilder.and(changeLog.memo.contains(cond.memo()));
    }
    if (hasText(cond.ipAddress())) {
      booleanBuilder.and(changeLog.ipAddress.contains(cond.ipAddress()));
    }
    if (cond.atFrom() != null) {
      booleanBuilder.and(changeLog.at.goe(cond.atFrom()));
    }
    if (cond.atTo() != null) {
      booleanBuilder.and(changeLog.at.loe(cond.atTo()));
    }
    return booleanBuilder;
  }

  private void cursorByCond(BooleanBuilder booleanBuilder, ChangeLogSearchCond cond) {
    if (!hasText(cond.cursor()) || cond.idAfter() == null) {
      return;
    }

    if ("ipAddress".equals(cond.sortField())) {
      if ("asc".equals(cond.sortDirection())) {
        booleanBuilder.and(
            changeLog
                .ipAddress
                .gt(cond.cursor())
                .or(changeLog.ipAddress.eq(cond.cursor()).and(changeLog.id.gt(cond.idAfter()))));
      } else {
        booleanBuilder.and(
            changeLog
                .ipAddress
                .lt(cond.cursor())
                .or(changeLog.ipAddress.eq(cond.cursor()).and(changeLog.id.lt(cond.idAfter()))));
      }
      return;
    }

    if ("at".equals(cond.sortField())) {
      LocalDateTime cursor = LocalDateTime.parse(cond.cursor());
      if ("asc".equals(cond.sortDirection())) {
        booleanBuilder.and(
            changeLog
                .at
                .gt(cursor)
                .or(changeLog.at.eq(cursor).and(changeLog.id.gt(cond.idAfter()))));
      } else {
        booleanBuilder.and(
            changeLog
                .at
                .lt(cursor)
                .or(changeLog.at.eq(cursor).and(changeLog.id.lt(cond.idAfter()))));
      }
      return;
    }

    throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST);
  }

  private void sortByCond(JPAQuery<ChangeLog> query, ChangeLogSearchCond cond) {
    if ("ipAddress".equals(cond.sortField())) {
      if ("asc".equals(cond.sortDirection())) {
        query.orderBy(changeLog.ipAddress.asc(), changeLog.id.asc());
      } else {
        query.orderBy(changeLog.ipAddress.desc(), changeLog.id.desc());
      }
      return;
    }

    if ("at".equals(cond.sortField())) {
      if ("asc".equals(cond.sortDirection())) {
        query.orderBy(changeLog.at.asc(), changeLog.id.asc());
      } else {
        query.orderBy(changeLog.at.desc(), changeLog.id.desc());
      }
      return;
    }

    throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST);
  }
}
