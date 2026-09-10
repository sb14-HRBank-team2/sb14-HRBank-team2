package com.sprint.hrbank.department.entity;

import com.sprint.hrbank.Employee.Employee;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;                 // 부서 ID

    private String name;                // 부서명
    private String description;         // 부서 설명
    private String establishedDate;     // 부서 설립일

    @OneToMany(mappedBy = "department")
    private List<Employee> employee;    // 직원

    @Builder
    public Department(String name, String description, String establishedDate) {
        this.name = name;
        this.description = description;
        this.establishedDate = establishedDate;
    }
}
