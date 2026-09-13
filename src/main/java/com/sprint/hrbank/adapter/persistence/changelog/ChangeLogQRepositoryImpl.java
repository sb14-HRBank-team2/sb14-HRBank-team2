package com.sprint.hrbank.adapter.persistence.changelog;

import static com.sprint.hrbank.domain.chagelog.QChangeLog.changeLog;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.BooleanBuilder;
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
    BooleanBuilder booleanBuilder = builder(cond);
    return jpaQueryFactory.selectFrom(changeLog).where(booleanBuilder).fetch();
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
}
