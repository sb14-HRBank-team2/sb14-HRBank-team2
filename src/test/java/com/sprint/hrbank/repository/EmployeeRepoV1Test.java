package com.sprint.hrbank.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.sprint.hrbank.entity.Department;
import com.sprint.hrbank.entity.Employee;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

@Slf4j
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
public class EmployeeRepoV1Test {

  private final DepartmentRepository departmentRepository;
  private final EmployeeRepository employeeRepository;

  @BeforeEach
  void before() {
    employeeRepository.deleteAll();
    departmentRepository.deleteAll();
  }

  @AfterEach
  void after() {
    employeeRepository.deleteAll();
    departmentRepository.deleteAll();
  }

  @Test
  void createEmployeeTest() {
    // given
    Department department = Department.create("개발2팀", "TDD");
    departmentRepository.save(department);
    Department findDepartment =
        departmentRepository
            .findById(department.getId())
            .orElseThrow(() -> new RuntimeException("없는 부서"));
    // when
    Employee employee =
        Employee.create(findDepartment, null, "박태양", "test@test.com", "tester", LocalDate.now());
    employeeRepository.save(employee);
    Employee findEmployee =
        employeeRepository
            .findById(employee.getId())
            .orElseThrow(() -> new RuntimeException("없는 직원"));
    // then
    assertThat(findEmployee.getEmployeeNumber()).isEqualTo(employee.getEmployeeNumber());
  }
}
