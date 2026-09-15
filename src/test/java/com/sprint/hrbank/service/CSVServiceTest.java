package com.sprint.hrbank.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.sprint.hrbank.application.backup.CSVService;
import com.sprint.hrbank.application.department.required.DepartmentRepository;
import com.sprint.hrbank.application.employee.required.EmployeeRepository;
import com.sprint.hrbank.common.config.QuerydslConfig;
import com.sprint.hrbank.domain.department.Department;
import com.sprint.hrbank.domain.employee.Employee;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;

@DataJpaTest(
    properties = {
      "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
      "spring.jpa.hibernate.ddl-auto=create-drop",
      "spring.data.jpa.repositories.bootstrap-mode=lazy"
    })
@Import({CSVService.class, QuerydslConfig.class})
class CSVServiceTest {

  @Autowired private CSVService csvService;
  @Autowired private DepartmentRepository departmentRepository;
  @Autowired private EmployeeRepository employeeRepository;

  @TempDir Path directory;

  @Test
  void 실제_직원_데이터를_CSV로_생성한다() throws Exception {
    Department department =
        departmentRepository.save(Department.create("백엔드", "백엔드 개발", LocalDate.of(2026, 9, 13)));
    Employee employee =
        employeeRepository.save(
            Employee.create(
                department, null, "김어진", "kim@naver.com", "대리", LocalDate.of(2026, 9, 14)));
    employeeRepository.flush();
    ReflectionTestUtils.setField(csvService, "fileDirectory", directory.toString());

    Path csvPath = csvService.createCSV();

    assertThat(Files.readAllLines(csvPath))
        .containsExactly(
            "ID,직원번호,이름,이메일,부서,직급,입사일,상태",
            employee.getId()
                + ","
                + employee.getEmployeeNumber()
                + ",김어진,kim@naver.com,백엔드,대리,2026-09-14,ACTIVE");
  }
}
