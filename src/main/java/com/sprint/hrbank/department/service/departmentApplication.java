package com.sprint.hrbank.department.service;

import com.sprint.hrbank.department.dto.DepartmentCreateRequest;
import com.sprint.hrbank.department.dto.DepartmentDto;
import com.sprint.hrbank.department.dto.DepartmentUpdateRequest;
import com.sprint.hrbank.department.entity.Department;
import com.sprint.hrbank.department.repository.departmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class departmentApplication {
    private final departmentRepository departmentRepository;

    // 부서 생성 - CREATE
    public DepartmentDto createDepartment(DepartmentCreateRequest request) {
        Department department = request.toEntity();
        Department entity = departmentRepository.save(department);
        return DepartmentDto.from(entity);
    }

    // 부서 단건 조회 - READ
    public DepartmentDto getDepartment(Integer id) {
        Department entity = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 부서는 존재하지 않습니다."));
        return DepartmentDto.from(entity);
    }

    // 부서 다건 조회 - READ
    public List<DepartmentDto> getDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(DepartmentDto::from)
                .toList();
    }

    // 부서 갱신 - UPDATE
//    public DepartmentDto updateDepartment(Integer id, DepartmentUpdateRequest request) {
//        departmentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("해당 부서는 존재하지 않습니다."));
//    }

    // 부서 삭제 - DELETE
    public void deleteDepartment(Integer id) {
        departmentRepository.deleteById(id);
    }
}
