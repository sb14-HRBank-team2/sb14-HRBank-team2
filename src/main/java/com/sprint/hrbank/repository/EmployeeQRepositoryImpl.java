package com.sprint.hrbank.repository;

import static com.sprint.hrbank.entity.QDepartment.department;
import static com.sprint.hrbank.entity.QEmployee.employee;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sprint.hrbank.entity.Employee;
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
    return jpaQueryFactory
        .select(employee)
        .from(employee)
        .join(employee.department, department)
        .where(booleanBuilder)
        .fetch();
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
