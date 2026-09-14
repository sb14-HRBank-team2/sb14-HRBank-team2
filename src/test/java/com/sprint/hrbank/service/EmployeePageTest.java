package com.sprint.hrbank.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.sprint.hrbank.adapter.persistence.employee.EmployeeSearchCond;
import com.sprint.hrbank.application.department.required.DepartmentRepository;
import com.sprint.hrbank.application.employee.dto.CursorPageResponseEmployeeDto;
import com.sprint.hrbank.application.employee.provided.query.EmployeePageMaker;
import com.sprint.hrbank.domain.department.Department;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@SpringBootTest
@Slf4j
@RequiredArgsConstructor
@TestConstructor(autowireMode = AutowireMode.ALL)
public class EmployeePageTest {

  private final EmployeePageMaker pageMaker;
  private final DepartmentRepository departmentRepository;

  /*
   * 현재 직원 15명 조건으로 테스트 진행 중.
   */

  @Test
  @DisplayName("직원 관리 기본 출력 확인.")
  void employeePageTest() {
    // 아래 부서 소속 직원 15명
    Department department =
        departmentRepository.findById(1L).orElseThrow(() -> new RuntimeException("조회오류"));
    // given
    // 기본 검색
    EmployeeSearchCond cond = EmployeeSearchCond.builder().build();
    // when
    CursorPageResponseEmployeeDto employeePage1 = pageMaker.getEmployeePage(cond);
    //
    EmployeeSearchCond condAfter =
        EmployeeSearchCond.builder()
            .cursor(employeePage1.nextCursor())
            .idAfter(employeePage1.nextIdAfter())
            .build();
    CursorPageResponseEmployeeDto employeePage2 = pageMaker.getEmployeePage(condAfter);
    // then
    // 1페이지 검증
    log.info("page1={}", employeePage1);
    log.info("totalElements={}", employeePage1.totalElements());
    assertThat(employeePage1.size()).isEqualTo(10);
    assertThat(cond.sortDirection()).isEqualTo("asc");
    assertThat(cond.sortField()).isEqualTo("name");
    // 2페이지 확인
    log.info("page2={}", employeePage2);
    log.info("totalElements={}", employeePage2.totalElements());
    assertThat(employeePage2.content().size()).isEqualTo(5);
    assertThat(employeePage2.hasNext()).isFalse();
  }
}
