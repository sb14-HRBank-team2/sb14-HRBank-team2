package com.sprint.hrbank.department.dto;

import com.sprint.hrbank.department.entity.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 부서 등록 요청
@Getter
@NoArgsConstructor
public class DepartmentCreateRequest {
    // 부서 이름
    private String name;
    // 부서 설명
    private String description;
    // 부서 설립일
    private String establishedDate;

    public Department toEntity() {
        return Department.builder()
                .name(this.name)
                .description(this.description)
                .establishedDate(this.establishedDate)
                .build();
    }
}
