package com.sprint.hrbank.department.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

// 부서 수정 요청
@Getter
@NoArgsConstructor
public class DepartmentUpdateRequest {
    // 부서명
    private String name;
    // 설명
    private String description;
    // 설립일
    private String establishedDate;

    public DepartmentUpdateRequest(String name, String description, String establishedDate) {
        this.name = name;
        this.description = description;
        this.establishedDate = establishedDate;
    }
}
