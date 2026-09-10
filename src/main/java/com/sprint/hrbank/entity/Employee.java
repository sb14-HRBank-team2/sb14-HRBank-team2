package com.sprint.hrbank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id; // 직원 아이디 (ERD의 Key)

    // 부서와의 다대일(N:1) 관계 매핑 (ERD의 Key2)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "profile_image_id")
    private Long profileImageId; // 프로필 이미지 id (파일 테이블 연동)

    private String name; // 이름 (ERD의 Field)

    private String email; // 이메일 (ERD의 Field2)

    private String employeeNumber; // 사원 번호 (ERD의 Field3)

    private String position; // 직함 (ERD의 Field5)

    private String joinDate; // 입사일 (ERD의 Field6)

    private String status; // 상태 (ERD의 Field7)
}