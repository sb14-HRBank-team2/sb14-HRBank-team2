package com.sprint.hrbank.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.sprint.hrbank.application.changelog.ChangeLogQueryService;
import com.sprint.hrbank.application.changelog.DiffService;
import com.sprint.hrbank.application.changelog.dto.ChangeLogDetailDto;
import com.sprint.hrbank.application.changelog.dto.DiffCreateRequestDto;
import com.sprint.hrbank.application.changelog.required.ChangeLogRepository;
import com.sprint.hrbank.application.department.required.DepartmentRepository;
import com.sprint.hrbank.application.employee.required.EmployeeRepository;
import com.sprint.hrbank.domain.chagelog.ChangeLog;
import com.sprint.hrbank.domain.chagelog.ChangeType;
import com.sprint.hrbank.domain.department.Department;
import com.sprint.hrbank.domain.employee.Employee;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(
    properties = {
      "spring.datasource.url=jdbc:h2:mem:changelog-detail;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
      "spring.datasource.driver-class-name=org.h2.Driver",
      "spring.datasource.username=sa",
      "spring.datasource.password=",
      "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
      "spring.jpa.hibernate.ddl-auto=create-drop"
    })
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
class ChangeLogDetailTest {

  private final ChangeLogQueryService changeLogQueryService;
  private final ChangeLogRepository changeLogRepository;
  private final EmployeeRepository employeeRepository;
  private final DepartmentRepository departmentRepository;
  private final DiffService diffService;

  @Test
  @DisplayName("변경 이력 상세에 직원 정보와 변경 항목을 포함한다")
  void getChangeLogDetail() {
    Department department =
        departmentRepository.save(Department.create("백엔드", "개발팀", LocalDate.of(2026, 1, 1)));
    Employee employee =
        employeeRepository.save(
            Employee.create(
                department, 10L, "김워진", "kim@naver.com", "백엔드 개발자", LocalDate.of(2026, 1, 1)));
    ChangeLog changeLog =
        changeLogRepository.save(
            ChangeLog.create(ChangeType.UPDATED, employee.getEmployeeNumber(), "변경", "127.0.0.1"));
    diffService.create(new DiffCreateRequestDto("position", "사원", "대리"), changeLog.getId());

    ChangeLogDetailDto result = changeLogQueryService.getChangeLogDetail(changeLog.getId());

    assertThat(result.id()).isEqualTo(changeLog.getId());
    assertThat(result.employeeName()).isEqualTo("김워진");
    assertThat(result.profileImageId()).isEqualTo(10L);
    assertThat(result.diffs())
        .singleElement()
        .satisfies(diff -> assertThat(diff.propertyName()).isEqualTo("position"));
  }

  @Test
  @DisplayName("삭제된 직원의 이력도 상세 조회한다")
  void getChangeLogDetailForDeletedEmployee() {
    ChangeLog changeLog =
        changeLogRepository.save(
            ChangeLog.create(ChangeType.DELETED, "EMP-DELETED", "직원 삭제", "127.0.0.1"));

    ChangeLogDetailDto result = changeLogQueryService.getChangeLogDetail(changeLog.getId());

    assertThat(result.employeeName()).isNull();
    assertThat(result.profileImageId()).isNull();
    assertThat(result.diffs()).isEmpty();
  }
}
