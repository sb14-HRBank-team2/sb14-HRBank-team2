package com.sprint.hrbank.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 부서 ID

    @Column(nullable = false)
    private String name; // 부서명

    @Column(nullable = false)
    private String description; // 부서 설명

    @Column(nullable = false)
    private LocalDateTime establishedDate; // 부서 설립일

    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>(); // 직원

    private Department(String name, String description, LocalDateTime establishedDate) {
        this.name = name;
        this.description = description;
        this.establishedDate = establishedDate;
    }

    public static Department create(String name, String description, LocalDateTime establishedDate){
        return new Department(name, description, establishedDate);
    }

    // 부서 필드 수정하기
    public void updateInfo(String name, String description, LocalDateTime establishedDate) {
        this.name = name;
        this.description = description;
        this.establishedDate = establishedDate;
    }
}
