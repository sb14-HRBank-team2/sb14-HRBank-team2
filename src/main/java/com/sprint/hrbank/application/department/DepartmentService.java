package com.sprint.hrbank.application.department;

import com.sprint.hrbank.application.department.provided.DepartmentFinder;
import com.sprint.hrbank.application.department.required.DepartmentRepository;
import com.sprint.hrbank.common.exception.CustomRuntimeException;
import com.sprint.hrbank.common.exception.ExceptionType;
import com.sprint.hrbank.domain.department.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DepartmentService implements DepartmentFinder {

  private final DepartmentRepository departmentRepository;

  //  private final EmployeeFinderByDepartment finder;

  //  @Override
  //  public DepartmentDto getByDepartmentId(Integer departmentId) {
  //    Department department =
  //        departmentRepository
  //            .findById(departmentId)
  //            .orElseThrow(() -> new CustomRuntimeException(ExceptionType.DEPARTMENT_NOT_FOUND));
  //    List<Employee> employees = finder.getByDepartmentId(departmentId);
  //
  //    return DepartmentDto.builder()
  //        .id(department.getId())
  //        .description(department.getDescription())
  //        .establishedDate(department.getEstablishedDate())
  //        .name(department.getName())
  //        .employeeCount(employees.size())
  //        .build();
  //  }

  @Override
  public Department getById(Integer departmentId) {
    Department department =
        departmentRepository
            .findById(departmentId)
            .orElseThrow(() -> new CustomRuntimeException(ExceptionType.DEPARTMENT_NOT_FOUND));
    return department;

    //    return DepartmentDto.builder().name(department.getName())
    //
    // .establishedDate(department.getEstablishedDate()).description(department.getDescription())
    //        .id(department.getId()).build();
  }
}
