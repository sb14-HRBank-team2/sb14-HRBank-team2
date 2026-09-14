package com.sprint.hrbank.adapter.webapi.department;

import com.sprint.hrbank.adapter.persistence.department.DepartmentSearchCond;
import com.sprint.hrbank.application.department.DepartmentCommandService;
import com.sprint.hrbank.application.department.DepartmentQueryService;
import com.sprint.hrbank.application.department.dto.CursorPageResponseDepartmentDto;
import com.sprint.hrbank.application.department.dto.DepartmentCreateRequest;
import com.sprint.hrbank.application.department.dto.DepartmentDto;
import com.sprint.hrbank.application.department.dto.DepartmentUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departments")
public class DepartmentController {

  private final DepartmentCommandService departmentCommandService;
  private final DepartmentQueryService departmentQueryService;

  @GetMapping
  public ResponseEntity<CursorPageResponseDepartmentDto> getDepartments(
      @ModelAttribute DepartmentSearchCond cond) {
    CursorPageResponseDepartmentDto response = departmentQueryService.getDepartmentPage(cond);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<DepartmentDto> createDepartment(
      @RequestBody DepartmentCreateRequest request) {
    DepartmentDto reponse = departmentCommandService.create(request);
    return ResponseEntity.ok(reponse);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DepartmentDto> getDepartment(@PathVariable Long id) {
    DepartmentDto response = departmentQueryService.getByDepartmentId(id);
    return ResponseEntity.ok(response);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<DepartmentDto> patchDepartment(
      @PathVariable Long id, @RequestBody DepartmentUpdateRequest request) {
    DepartmentDto response = departmentCommandService.update(id, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
    departmentCommandService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
