package com.sprint.hrbank.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String employeeNumber;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "department_id")
    private Department departmentId; //FK Department

    private String departmentName;
    private String position;
    private String hireDate;
    private EmployeeStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profileimage_id")
    private File profileImageId; //FK file


    public void resign() {
        this.status = EmployeeStatus.휴직중;
    }

    public void working() {
        this.status  = EmployeeStatus.재직중;
    }

}
