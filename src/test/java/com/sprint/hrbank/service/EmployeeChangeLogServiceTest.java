package com.sprint.hrbank.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.sprint.hrbank.application.changelog.required.ChangeLogRepository;
import com.sprint.hrbank.application.department.required.DepartmentRepository;
import com.sprint.hrbank.application.employee.EmployeeCommandService;
import com.sprint.hrbank.application.employee.dto.EmployeeCreateRequest;
import com.sprint.hrbank.application.employee.dto.EmployeeDto;
import com.sprint.hrbank.application.employee.dto.EmployeeUpdateRequest;
import com.sprint.hrbank.application.employee.required.EmployeeRepository;
import com.sprint.hrbank.domain.chagelog.ChangeLog;
import com.sprint.hrbank.domain.department.Department;
import com.sprint.hrbank.domain.employee.Employee;
import com.sprint.hrbank.domain.employee.EmployeeStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
@Transactional
public class EmployeeChangeLogServiceTest {

  private final EmployeeCommandService employeeService;
  private final EmployeeRepository employeeRepository;
  private final DepartmentRepository departmentRepository;
  private final ChangeLogRepository changeLogRepository;

  @Test
  @DisplayName("직원을 생성하면 상세 정보 DTO가 반환되고 ChangeLog에 생성 이력이 남아야 한다.")
  void registerEmployeeTest() {
    // Given (준비)
    // 부서 생성
    Department department = Department.create("BE 개발팀", "백엔드 개발하는 팀", LocalDate.now());
    departmentRepository.save(department);

    // 부서에 소속 직원 생성
    Employee employee =
        Employee.create(department, 1, "신상엽", "sangyeop@test.com", "백엔드 개발자", LocalDate.now());

    // 직원 생성 요청
    EmployeeCreateRequest request =
        new EmployeeCreateRequest(
            employee.getName(),
            employee.getEmail(),
            employee.getDepartment().getId(),
            employee.getPosition(),
            employee.getHireDate(),
            null);
    // 가짜 ip 주소 생성
    String testIpAddress = "192.168.0.99";

    // When (실행)
    EmployeeDto employeeDetailDto = employeeService.register(request, testIpAddress);

    // Then (검증)
    assertThat(employeeDetailDto.name()).isEqualTo("신상엽");
    assertThat(employeeDetailDto.email()).isEqualTo("sangyeop@test.com");
    assertThat(employeeDetailDto.departmentName()).isEqualTo("BE 개발팀");
    assertThat(employeeDetailDto.status()).isEqualTo(EmployeeStatus.ACTIVE);

    // 생성한 직원의 이력이 남아있는가?
    List<ChangeLog> logs = changeLogRepository.findAll();
    assertThat(logs).hasSize(1);

    ChangeLog savedLog = logs.get(0);
    assertThat(savedLog.getEmployeeNumber()).isEqualTo(employeeDetailDto.employeeNumber());
    assertThat(savedLog.getType().name()).isEqualTo("CREATED");
    assertThat(savedLog.getIpAddress()).isEqualTo(testIpAddress);
  }

  @Test
  @DisplayName("직원을 수정하면 정보가 변경되고 ChangeLog에 수정 이력이 남아야 한다.")
  void updateEmployeeTest() {
    // Given (준비)
    // 부서 생성
    Department department = Department.create("BE 개발팀", "백엔드 개발하는 팀", LocalDate.now());
    departmentRepository.save(department);

    // 부서에 소속 직원 생성
    Employee employee =
        Employee.create(department, 1, "신상엽", "sangyeop@test.com", "백엔드 개발자", LocalDate.now());
    employeeRepository.save(employee);

    // 저장한 소속 직원 id, 사원 번호 얻기
    Integer employeeId = employee.getId();
    String employeeNumber = employee.getEmployeeNumber();

    // 직원 수정 요청
    EmployeeUpdateRequest request =
        new EmployeeUpdateRequest(
            employee.getName(),
            employee.getEmail(),
            employee.getDepartment().getId(),
            "백엔드 개발 팀장",
            employee.getHireDate(),
            employee.getStatus(),
            "직급 변경");

    // 가짜 ip 주소 생성
    String testIpAddress = "192.168.0.99";

    // When (실행)
    EmployeeDto employeeDetailDto = employeeService.update(employeeId, request, testIpAddress);

    // Then (검증)
    assertThat(employeeDetailDto.name()).isEqualTo("신상엽");
    assertThat(employeeDetailDto.email()).isEqualTo("sangyeop@test.com");
    assertThat(employeeDetailDto.departmentName()).isEqualTo("BE 개발팀");
    assertThat(employeeDetailDto.position()).isEqualTo("백엔드 개발 팀장");
    assertThat(employeeDetailDto.status()).isEqualTo(EmployeeStatus.ACTIVE);

    // 수정한 직원의 이력이 남아있는가?
    List<ChangeLog> logs = changeLogRepository.findAll();
    assertThat(logs).hasSize(1);

    ChangeLog savedLog = logs.get(0);
    assertThat(savedLog.getEmployeeNumber()).isEqualTo(employeeNumber);
    assertThat(savedLog.getType().name()).isEqualTo("UPDATED");
    assertThat(savedLog.getIpAddress()).isEqualTo(testIpAddress);
    assertThat(savedLog.getMemo()).isEqualTo("직급 변경");
  }

  @Test
  @DisplayName("직원을 삭제하면 DB에서 완전히 지워지고 ChangeLog에 삭제 이력은 남아야 한다.")
  void deleteEmployeeTest() {
    // Given (준비)
    // 부서 생성
    Department department = Department.create("BE 개발팀", "백엔드 개발하는 팀", LocalDate.now());
    departmentRepository.save(department);

    // 부서에 소속 직원 생성
    Employee employee =
        Employee.create(department, 1, "신상엽", "sangyeop@test.com", "백엔드 개발자", LocalDate.now());
    employeeRepository.save(employee);

    // 저장한 소속 직원 id, 사원 번호 얻기
    Integer employeeId = employee.getId();
    String employeeNumber = employee.getEmployeeNumber();

    // 가짜 ip 주소 생성
    String testIpAddress = "192.168.0.99";

    // When (실행)
    employeeService.deleteById(employeeId, testIpAddress);

    // Then (검증)
    // 삭제한 아이디를 조회했을 때, 데이터가 비어있는가?
    Optional<Employee> deletedId = employeeRepository.findById(employeeId);
    assertThat(deletedId).isEmpty();

    // 삭제한 아이디를 ChangeLog에서 검색했을 때, 기록이 남아있는가?
    List<ChangeLog> logs = changeLogRepository.findAll();
    assertThat(logs).hasSize(1);

    ChangeLog savedLog = logs.get(0);
    assertThat(savedLog.getEmployeeNumber()).isEqualTo(employeeNumber);
    assertThat(savedLog.getType().name()).isEqualTo("DELETED");
    assertThat(savedLog.getIpAddress()).isEqualTo(testIpAddress);
  }
}
