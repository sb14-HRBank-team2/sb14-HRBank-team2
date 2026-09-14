package com.sprint.hrbank.adapter.persistence.department;

import static com.sprint.hrbank.entity.QDepartment.department;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sprint.hrbank.domain.department.Department;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class DepartmentQRepositoryImpl implements DepartmentQRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Department> search(DepartmentSearchCond cond) {
    BooleanBuilder booleanBuilder = builder(cond);

    cursorByCond(booleanBuilder, cond);

    JPAQuery<Department> jpaQuery = jpaQueryFactory.selectFrom(department).where(booleanBuilder);

    sortByCond(jpaQuery, cond);
    jpaQuery.limit(cond.size() + 1);

    return jpaQuery.fetch();
  }

  @Override
  public Long countByCondition(DepartmentSearchCond cond) {
    BooleanBuilder booleanBuilder = builder(cond);

    Long totalElements =
        jpaQueryFactory
            .select(department.count())
            .from(department)
            .where(booleanBuilder)
            .fetchOne();
    return totalElements == null ? 0L : totalElements;
  }

  private void cursorByCond(BooleanBuilder booleanBuilder, DepartmentSearchCond cond) {
    if (!hasText(cond.cursor()) && cond.idAfter() == null) {
      return;
    }
    Integer idAfter = cond.idAfter() != null ? cond.idAfter().intValue() : null;

    if ("name".equals(cond.sortField())) {
      if ("asc".equals(cond.sortDirection())) {
        booleanBuilder.and(
            department
                .name
                .gt(cond.cursor())
                .or(department.name.eq(cond.cursor()).and(department.id.gt(idAfter))));
      } else {
        booleanBuilder.and(
            department
                .name
                .lt(cond.cursor())
                .or(department.name.eq(cond.cursor()).and(department.id.lt(idAfter))));
      }
    }

    if ("establishedDate".equals(cond.sortField())) {
      if ("asc".equals(cond.sortDirection())) {
        booleanBuilder.and(
            department
                .establishedDate
                .gt(LocalDate.parse(cond.cursor()))
                .or(
                    department
                        .establishedDate
                        .eq(LocalDate.parse(cond.cursor()))
                        .and(department.id.gt(idAfter))));
      } else {
        booleanBuilder.and(
            department
                .establishedDate
                .lt(LocalDate.parse(cond.cursor()))
                .or(
                    department
                        .establishedDate
                        .eq(LocalDate.parse(cond.cursor()))
                        .and(department.id.lt(idAfter))));
      }
    }
  }

  private void sortByCond(JPAQuery<Department> jpaQuery, DepartmentSearchCond cond) {
    if ("name".equals(cond.sortField())) {
      if (cond.sortDirection().equals("asc")) {
        jpaQuery.orderBy(department.name.asc(), department.id.asc());
      } else {
        jpaQuery.orderBy(department.name.desc(), department.id.desc());
      }
    }

    if ("establishedDate".equals(cond.sortField())) {
      if (cond.sortDirection().equals("asc")) {
        jpaQuery.orderBy(department.establishedDate.asc(), department.id.asc());
      } else {
        jpaQuery.orderBy(department.establishedDate.desc(), department.id.desc());
      }
    }
  }

  private BooleanBuilder builder(DepartmentSearchCond cond) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();

    if (hasText(cond.nameOrDescription())) {
      booleanBuilder.and(
          department
              .name
              .contains(cond.nameOrDescription())
              .or(department.description.contains(cond.nameOrDescription())));
    }
    return booleanBuilder;
  }
}
