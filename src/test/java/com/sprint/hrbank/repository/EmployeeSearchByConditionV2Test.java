package com.sprint.hrbank.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sprint.hrbank.adapter.persistence.employee.EmployeeSearchCond;
import com.sprint.hrbank.application.department.required.DepartmentRepository;
import com.sprint.hrbank.application.employee.required.EmployeeRepository;
import com.sprint.hrbank.domain.department.Department;
import com.sprint.hrbank.domain.employee.Employee;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
public class EmployeeSearchByConditionV2Test {

  private final EmployeeRepository employeeRepository;
  private final DepartmentRepository departmentRepository;

  @BeforeEach
  void before() {}

  @Test
  @Transactional
  @Commit
  void searchByCondition() {
    // given
    Department dept = Department.create("테스트112", "복합검색 테스트", LocalDate.of(2026, 8, 29));
    departmentRepository.save(dept);
    Employee emp =
        Employee.create(
            dept, null, "테스터1112", "test@test.com112", "tester", LocalDate.of(2026, 9, 2));

    employeeRepository.save(emp);

    EmployeeSearchCond cond =
        EmployeeSearchCond.builder().departmentName("테스").nameOrEmail("te").build();

    // when
    List<Employee> result = employeeRepository.search(cond);
    // then
    log.info("cond={}", cond);
    log.info("emp={}", emp);
    log.info("result={}", result);
    assertThat(result.size()).isEqualTo(2);
    //    assertThat(result.iterator().next().getId()).isEqualTo(emp.getId());
  }
}
