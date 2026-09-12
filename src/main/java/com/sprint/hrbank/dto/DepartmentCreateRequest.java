package com.sprint.hrbank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sprint.hrbank.entity.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DepartmentCreateRequest {
    private String name; // 부서 이름
    private String description; // 부서 설명

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate establishedDate; // 부서 설립일

    public Department toEntity() {
        return Department.create(
                this.name,
                this.description,
                this.establishedDate.atStartOfDay() );
    }
}

