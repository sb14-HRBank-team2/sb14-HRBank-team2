package com.sprint.hrbank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long id; // 부서 아이디 (ERD의 Key)

    private String name; // 이름

    private String description; // 설명

    private String establishedDate; // 설립일
}