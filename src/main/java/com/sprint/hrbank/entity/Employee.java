package com.sprint.hrbank.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 직원 ID

    private String name; // 직원 이름
    private String email; // 이메일
    private Integer employeeNumber; // 사원 번호
    private String position; // 직함
    private LocalDateTime hireDate; // 입사일
    private String status; // 상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    private Employee(String name, String email, Integer employeeNumber, String position, LocalDateTime hireDate, String status, Department department) {
        this.name = name;
        this.email = email;
        this.employeeNumber = employeeNumber;
        this.position = position;
        this.hireDate = hireDate;
        this.status = status;
        this.department = department;
    }

    public static Employee create(String name, String email, Integer employeeNumber, String position, LocalDateTime hireDate, String status, Department department) {
        return new Employee(
                name,
                email,
                employeeNumber,
                position,
                hireDate,
                status,
                department
        );
    }
}
