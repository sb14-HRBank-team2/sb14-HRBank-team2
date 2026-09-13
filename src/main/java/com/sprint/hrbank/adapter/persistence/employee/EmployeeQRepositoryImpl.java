package com.sprint.hrbank.adapter.persistence.employee;

import static com.sprint.hrbank.domain.department.QDepartment.department;
import static com.sprint.hrbank.domain.employee.QEmployee.employee;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sprint.hrbank.domain.employee.Employee;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class EmployeeQRepositoryImpl implements EmployeeQRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Employee> search(EmployeeSearchCond cond) {
    BooleanBuilder booleanBuilder = builder(cond);

    cursorByCond(booleanBuilder, cond);

    JPAQuery<Employee> jpaQuery =
        jpaQueryFactory
            .selectFrom(employee)
            .join(employee.department, department)
            .fetchJoin()
            .where(booleanBuilder);

    sortByCond(jpaQuery, cond);
    jpaQuery.limit(cond.size() + 1);

    return jpaQuery.fetch();
  }

  @Override
  public Long countByCondition(EmployeeSearchCond cond) {
    BooleanBuilder booleanBuilder = builder(cond);

    Long totalElements =
        jpaQueryFactory
            .select(employee.count())
            .from(employee)
            .join(employee.department, department)
            .where(booleanBuilder)
            .fetchOne();

    return totalElements == null ? 0L : totalElements;
  }

  private void cursorByCond(BooleanBuilder booleanBuilder, EmployeeSearchCond cond) {
    if (!hasText(cond.cursor()) && cond.idAfter() == null) {
      return;
    }

    if ("name".equals(cond.sortField())) {
      if ("asc".equals(cond.sortDirection())) {
        booleanBuilder.and(
            employee
                .name
                .gt(cond.cursor())
                .or(employee.name.eq(cond.cursor()).and(employee.id.gt(cond.idAfter()))));
      } else {
        booleanBuilder.and(
            employee
                .name
                .lt(cond.cursor())
                .or(employee.name.eq(cond.cursor()).and(employee.id.lt(cond.idAfter()))));
      }
    }

    if ("employeeNumber".equals(cond.sortField())) {
      if ("asc".equals(cond.sortDirection())) {
        booleanBuilder.and(
            employee
                .employeeNumber
                .gt(cond.cursor())
                .or(employee.employeeNumber.eq(cond.cursor()).and(employee.id.gt(cond.idAfter()))));
      } else {
        booleanBuilder.and(
            employee
                .employeeNumber
                .lt(cond.cursor())
                .or(employee.employeeNumber.eq(cond.cursor()).and(employee.id.lt(cond.idAfter()))));
      }
    }

    if ("hireDate".equals(cond.sortField())) {
      if ("asc".equals(cond.sortDirection())) {
        booleanBuilder.and(
            employee
                .hireDate
                .gt(LocalDate.parse(cond.cursor()))
                .or(
                    employee
                        .hireDate
                        .eq(LocalDate.parse(cond.cursor()))
                        .and(employee.id.gt(cond.idAfter()))));
      } else {
        booleanBuilder.and(
            employee
                .hireDate
                .lt(LocalDate.parse(cond.cursor()))
                .or(
                    employee
                        .hireDate
                        .eq(LocalDate.parse(cond.cursor()))
                        .and(employee.id.lt(cond.idAfter()))));
      }
    }
  }

  private void sortByCond(JPAQuery<Employee> jpaQuery, EmployeeSearchCond cond) {

    if ("name".equals(cond.sortField())) {
      if (cond.sortDirection().equals("asc")) {
        jpaQuery.orderBy(employee.name.asc(), employee.id.asc());
      } else {
        jpaQuery.orderBy(employee.name.desc(), employee.id.desc());
      }
    }

    if ("employeeNumber".equals(cond.sortField())) {
      if (cond.sortDirection().equals("asc")) {
        jpaQuery.orderBy(employee.employeeNumber.asc(), employee.id.asc());
      } else {
        jpaQuery.orderBy(employee.employeeNumber.desc(), employee.id.desc());
      }
    }

    if ("hireDate".equals(cond.sortField())) {
      if (cond.sortDirection().equals("asc")) {
        jpaQuery.orderBy(employee.hireDate.asc(), employee.id.asc());
      } else {
        jpaQuery.orderBy(employee.hireDate.desc(), employee.id.desc());
      }
    }
  }

  private BooleanBuilder builder(EmployeeSearchCond cond) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();

    if (hasText(cond.nameOrEmail())) {
      booleanBuilder.and(
          employee
              .name
              .contains(cond.nameOrEmail())
              .or(employee.email.contains(cond.nameOrEmail())));
    }
    if (hasText(cond.departmentName())) {
      booleanBuilder.and(employee.department.name.contains(cond.departmentName()));
    }
    if (hasText(cond.position())) {
      booleanBuilder.and(employee.position.contains(cond.position()));
    }
    if (hasText(cond.employeeNumber())) {
      booleanBuilder.and(employee.employeeNumber.contains(cond.employeeNumber()));
    }
    if (cond.hireDateFrom() != null) {
      booleanBuilder.and(employee.hireDate.goe(cond.hireDateFrom()));
    }
    if (cond.hireDateTo() != null) {
      booleanBuilder.and(employee.hireDate.loe(cond.hireDateTo()));
    }
    if (cond.status() != null) {
      booleanBuilder.and(employee.status.eq(cond.status()));
    }
    return booleanBuilder;
  }
}
