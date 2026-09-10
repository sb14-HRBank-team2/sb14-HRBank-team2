package com.sprint.hrbank.department.dto;

import com.sprint.hrbank.department.entity.Department;
import lombok.Getter;

// 부서 정보
@Getter
public class DepartmentDto {
    private final Integer id;               // 부서 ID
    private final String name;              // 부서 이름
    private final String description;       // 부서 설명
    private final String establishedDate;   // 부서 설립일
    private final Integer employeeCount;    // 소속 직원 수

    private DepartmentDto(Integer id, String name, String description, String establishedDate, Integer employeeCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.establishedDate = establishedDate;
        this.employeeCount = employeeCount;
    }

    public static DepartmentDto from(Department entity) {
        return new DepartmentDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getEstablishedDate(),
                0
        );
    }
}
