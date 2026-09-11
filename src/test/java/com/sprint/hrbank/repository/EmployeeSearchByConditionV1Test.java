package com.sprint.hrbank.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sprint.hrbank.entity.Department;
import com.sprint.hrbank.entity.Employee;
import com.sprint.hrbank.entity.EmployeeStatus;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
public class EmployeeSearchByConditionV1Test {

  private final EmployeeRepository employeeRepository;
  private final DepartmentRepository departmentRepository;

  @BeforeEach
  void before() {
    employeeRepository.deleteAll();
    departmentRepository.deleteAll();
  }

  @Test
  @Transactional
  void searchByCondition() {
    // given
    Department dept = Department.create("테스트9", "복합검색 테스트", LocalDate.of(2026, 8, 29));
    departmentRepository.save(dept);
    Employee emp =
        Employee.create(dept, null, "테스터", "test@test.com8", "tester", LocalDate.of(2026, 9, 2));

    employeeRepository.save(emp);

    EmployeeSearchCond cond =
        new EmployeeSearchCond(
            "test@t",
            emp.getEmployeeNumber(),
            "테",
            "tes",
            LocalDate.of(2026, 9, 1),
            LocalDate.of(2026, 9, 4),
            EmployeeStatus.ACTIVE);
    // when
    List<Employee> result = employeeRepository.search(cond);
    // then
    log.info("cond={}", cond);
    log.info("emp={}", emp);
    log.info("result={}", result);
    assertThat(result.size()).isEqualTo(1);
    assertThat(result.iterator().next().getId()).isEqualTo(emp.getId());
  }
}
