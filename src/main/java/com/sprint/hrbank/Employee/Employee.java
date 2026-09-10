package com.sprint.hrbank.Employee;

import com.sprint.hrbank.department.entity.Department;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;               // 직원 ID

    private String name;              // 직원 이름
    private String email;             // 이메일
    private Integer employeeNumber;   // 사원 번호
    private String position;          // 직함
    private Integer hireDate;         // 입사일
    private String status;            // 상태

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Builder
    private Employee(String name, String email, Integer employeeNumber, String position, Integer hireDate, String status, Department department) {
        this.name = name;
        this.email = email;
        this.employeeNumber = employeeNumber;
        this.position = position;
        this.hireDate = hireDate;
        this.status = status;
        this.department = department;
    }
}
