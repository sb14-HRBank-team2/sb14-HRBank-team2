package com.sprint.hrbank.adapter.persistence.employee;

import static com.sprint.hrbank.domain.department.QDepartment.department;
import static com.sprint.hrbank.domain.employee.QEmployee.employee;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sprint.hrbank.application.employee.dto.EmployeeDistributionDto;
import com.sprint.hrbank.application.employee.dto.EmployeeTrendDto;
import com.sprint.hrbank.application.employee.provided.query.EmployeeSearchCond;
import com.sprint.hrbank.common.exception.CustomRuntimeException;
import com.sprint.hrbank.common.exception.ExceptionType;
import com.sprint.hrbank.domain.employee.Employee;
import com.sprint.hrbank.domain.employee.EmployeeStatus;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class EmployeeQRepositoryImpl implements EmployeeQRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<EmployeeDistributionDto> searchDistribution(String groupBy) {
    StringExpression groupExpression;

    if ("department".equals(groupBy)) {
      groupExpression = department.name;
    } else if ("position".equals(groupBy)) {
      groupExpression = employee.position;
    } else {
      throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST);
    }

    NumberExpression<Long> countExpression = employee.count();

    List<Tuple> result =
        jpaQueryFactory
            .select(groupExpression, countExpression)
            .from(employee)
            .join(employee.department, department)
            .where(employee.status.eq(EmployeeStatus.ACTIVE))
            .groupBy(groupExpression)
            .orderBy(countExpression.desc(), groupExpression.asc())
            .fetch();
    Long sum = result.stream().mapToLong(tuple -> tuple.get(countExpression)).sum();

    return result.stream()
        .map(
            tuple -> {
              Long count = tuple.get(countExpression);
              double percentage = Math.round(count * 1000.0 / sum) / 10.0;
              return EmployeeDistributionDto.builder()
                  .percentage(percentage)
                  .groupKey(tuple.get(groupExpression))
                  .count(count)
                  .build();
            })
        .toList();
  }

  @Override
  public List<EmployeeTrendDto> searchTrend(String unit) {
    LocalDate today = LocalDate.now();
    List<LocalDate> hireDates =
        jpaQueryFactory
            .select(employee.hireDate)
            .from(employee)
            .where(employee.hireDate.loe(today))
            .orderBy(employee.hireDate.asc())
            .fetch();

    List<EmployeeTrendDto> result = new ArrayList<>();
    Long previousCount = 0L;
    LocalDate fromDate = getStartPoint(unit, today);

    for (int i = 0; i < 12; i++) {
      LocalDate endDate = getEntPoint(unit, fromDate);
      Long count = hireDates.stream().filter(hireDate -> hireDate.isBefore(endDate)).count();

      Long change = i == 0 ? 0L : count - previousCount;
      double changeRate;
      if (i == 0 || previousCount == 0) {
        changeRate = 100;
      } else {
        changeRate = Math.round(change * 1000.0 / previousCount) / 10.0;
      }
      result.add(
          EmployeeTrendDto.builder()
              .count(count)
              .date(fromDate)
              .change(change)
              .changeRate(changeRate)
              .build());
      previousCount = count;
      fromDate = endDate;
    }

    return result;
  }

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

  // 조회 시작일
  private LocalDate getStartPoint(String unit, LocalDate today) {
    return switch (unit) {
      case "day" -> today.minusDays(11);
      case "week" -> today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).minusWeeks(11);
      case "month" -> today.withDayOfMonth(1).minusMonths(11);
      case "quarter" -> {
        int quarter = (today.getMonthValue() - 1) / 3 * 3 + 1;
        yield today.withMonth(quarter).withDayOfMonth(1).minusMonths(33);
      }
      case "year" -> today.withDayOfYear(1).minusYears(11);
      default -> throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST);
    };
  }

  // 조회 마지막
  private LocalDate getEntPoint(String unit, LocalDate today) {
    return switch (unit.toLowerCase()) {
      case "day" -> today.plusDays(1);
      case "week" -> today.plusWeeks(1);
      case "month" -> today.plusMonths(1);
      case "quarter" -> today.plusMonths(3);
      case "year" -> today.plusYears(1);
      default -> throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST);
    };
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
