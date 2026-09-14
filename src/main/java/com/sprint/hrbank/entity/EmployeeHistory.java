package com.sprint.hrbank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "employee_history")
public class EmployeeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer employeeId; // 직원 ID

    private String changeContent; // 변경 내용 또는 수정 이력 상세

    private LocalDateTime modifiedAt; // 수정 일시
}