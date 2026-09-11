package com.sprint.hrbank.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.sprint.hrbank.domain.Department;
import com.sprint.hrbank.domain.Employee;
import com.sprint.hrbank.domain.Employee.EmployeeStatus;
import com.sprint.hrbank.dto.EmployeeDto;
import com.sprint.hrbank.repository.DepartmentRepository;
import com.sprint.hrbank.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class EmployeeServiceTest {
  @Autowired EmployeeService employeeService;
  @Autowired EmployeeRepository employeeRepository;
  @Autowired DepartmentRepository departmentRepository;

  @Test
  @DisplayName("직원 ID로 조회하면 상세 정보 DTO가 정상적으로 반환되어야 한다.")
  void getEmployeeDetailTest() {
    // Given (준비)
    // 부서 생성
    Department department = Department.create("BE 개발팀", "백엔드 개발하는 팀", LocalDate.now());
    departmentRepository.save(department);

    // 부서에 소속 직원 생성
    Employee employee =
        Employee.create(
            department,
            "신상엽",
            "sangyeop@test.com",
            "EMP-001",
            "백엔드 개발자",
            LocalDate.now(),
            EmployeeStatus.ACTIVE,
            1);

    // 저장한 소속 직원 id 얻기
    Employee savedEmployee = employeeRepository.save(employee);
    Integer id = savedEmployee.getId();

    // When (실행)
    EmployeeDto employeeDetailDto = employeeService.getEmployeeDetail(id);

    // Then (검증)
    assertThat(employeeDetailDto.name()).isEqualTo("신상엽");
    assertThat(employeeDetailDto.email()).isEqualTo("sangyeop@test.com");
    assertThat(employeeDetailDto.departmentName()).isEqualTo("BE 개발팀");
    assertThat(employeeDetailDto.status()).isEqualTo("ACTIVE");
  }
}
