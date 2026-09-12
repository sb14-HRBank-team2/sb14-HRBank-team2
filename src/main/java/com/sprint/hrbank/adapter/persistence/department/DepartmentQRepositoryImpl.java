package com.sprint.hrbank.adapter.persistence.department;

import static com.sprint.hrbank.domain.department.QDepartment.department;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sprint.hrbank.domain.department.Department;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DepartmentQRepositoryImpl implements DepartmentQRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Department> search(DepartmentSearchCond cond) {
    BooleanBuilder booleanBuilder = builder(cond);
    return jpaQueryFactory.select(department).from(department).where(booleanBuilder).fetch();
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
