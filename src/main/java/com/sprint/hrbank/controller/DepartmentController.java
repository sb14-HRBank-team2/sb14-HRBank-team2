package com.sprint.hrbank.controller;

import com.sprint.hrbank.dto.DepartmentCreateRequest;
import com.sprint.hrbank.dto.DepartmentDto;
import com.sprint.hrbank.dto.DepartmentUpdateRequest;
import java.util.List;

import com.sprint.hrbank.service.DepartmentApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DepartmentController {
  private final DepartmentApplication departmentApplication;

  // 부서 등록(POST)
  @RequestMapping(method = RequestMethod.POST, value = "/api/departments")
  public ResponseEntity<DepartmentDto> create(@RequestBody DepartmentCreateRequest request) {
    DepartmentDto response = departmentApplication.createDepartment(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  // 부서 상세 조회(GET)
  @RequestMapping(method = RequestMethod.GET, value = "/api/departments/{id}")
  public ResponseEntity<DepartmentDto> read(@PathVariable Integer id) {
    DepartmentDto response = departmentApplication.getDepartment(id);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  // 부서 목록 조회(GET)
  @RequestMapping(method = RequestMethod.GET, value = "/api/departments")
  public ResponseEntity<List<DepartmentDto>> readAll() {
    List<DepartmentDto> response = departmentApplication.getDepartments();
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  // 부서 수정(PATCH)
  @RequestMapping(method = RequestMethod.PATCH, value = "/api/departments/{id}")
  public ResponseEntity<DepartmentDto> update(
      @PathVariable Integer id, @RequestBody DepartmentUpdateRequest request) {
    DepartmentDto response = departmentApplication.updateDepartment(id, request);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  // 부서 삭제(DELETE)
  @RequestMapping(method = RequestMethod.DELETE, value = "/api/departments/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    departmentApplication.deleteDepartment(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
