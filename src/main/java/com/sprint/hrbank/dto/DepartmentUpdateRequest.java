package com.sprint.hrbank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// 부서 수정 요청
@Getter
@NoArgsConstructor
public class DepartmentUpdateRequest {
    private String name; // 부서명
    private String description; // 설명

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate establishedDate; // 설립일

    public DepartmentUpdateRequest(String name, String description, LocalDate establishedDate) {
        this.name = name;
        this.description = description;
        this.establishedDate = establishedDate;
    }
}

