package com.sprint.hrbank.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.sprint.hrbank.entity.Department;
import com.sprint.hrbank.entity.Employee;
import com.sprint.hrbank.entity.EmployeeStatus;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

@Slf4j
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
public class EntityTest {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    Department department = Department.create();
    Department department2 = Department.create();

    Employee employee = Employee.create(department,
        1L,
        "test",
        "test",
        "test",
        "test",
        LocalDate.now(),
        EmployeeStatus.WORKING
    );
    Employee employee2 = Employee.create(department2,
        1L,
        "test",
        "test",
        "test",
        "test",
        LocalDate.now(),
        EmployeeStatus.WORKING
    );


    @Test
    void createMemberTest() {
        log.info("employeerepo={}", employeeRepository.getClass());
        //save
        Department saveDepartment = departmentRepository.save(department);
        log.info("department={}", department);
        Department findDepartment = departmentRepository.findById(saveDepartment.getId())
            .orElse(null);
        Employee employee = Employee.create(findDepartment,
            1L,
            "test",
            "test",
            "test",
            "test",
            LocalDate.now(),
            EmployeeStatus.WORKING
        );
        Employee saveEmployee = employeeRepository.save(employee);
        log.info("save={}", saveEmployee);
        //find
        Employee find = employeeRepository.findById(employee.getId()).orElse(null);

        assertThat(saveEmployee.getId()).isEqualTo(find.getId());

    }
}
