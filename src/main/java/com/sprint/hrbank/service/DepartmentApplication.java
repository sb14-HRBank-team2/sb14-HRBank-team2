package com.sprint.hrbank.service;

import com.sprint.hrbank.dto.DepartmentCreateRequest;
import com.sprint.hrbank.dto.DepartmentDto;
import com.sprint.hrbank.dto.DepartmentUpdateRequest;
import com.sprint.hrbank.entity.Department;
import com.sprint.hrbank.repository.DepartmentRepository;
import java.util.List;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentApplication {
  private final DepartmentRepository departmentRepository;

  // 부서 생성 - CREATE
  @Transactional
  public DepartmentDto createDepartment(DepartmentCreateRequest request) {
    Department department = request.toEntity();
    Department entity = departmentRepository.save(department);
    return DepartmentDto.from(entity);
  }

  // 부서 상세 조회 - READ
  public DepartmentDto getDepartment(Integer id) {
    Department entity =
        departmentRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("해당 부서는 존재하지 않습니다."));
    return DepartmentDto.from(entity);
  }

  // 부서 목록 조회 - READ
  public List<DepartmentDto> getDepartments() {
    List<Department> departments = departmentRepository.findAll();
    return departments
            .stream()
            .map(DepartmentDto::from)
            .toList();
  }

  // 부서 수정 - UPDATE
  @Transactional
  public DepartmentDto updateDepartment(Integer id, DepartmentUpdateRequest request) {
    Department entity =
        departmentRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("해당 부서는 존재하지 않습니다."));
    // 부서 필드 수정
    entity.updateInfo(
            request.getName(),
            request.getDescription(),
            request.getEstablishedDate().atStartOfDay()
    );
    return DepartmentDto.from(entity);
  }

  // 부서 삭제 - DELETE
  @Transactional
  public void deleteDepartment(Integer id) {
    departmentRepository.deleteById(id);
  }
}
