package com.sprint.hrbank.service;

import com.sprint.hrbank.entity.Department;
import com.sprint.hrbank.exception.CustomRuntimeException;
import com.sprint.hrbank.exception.ExceptionType;
import com.sprint.hrbank.repository.DepartmentRepository;
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
    //        DepartmentDto.builder().name(department.getName())
    //
    // .establishedDate(department.getEstablishedDate()).description(department.getDescription())
    //        .id(department.getId()).build();
  }
}
