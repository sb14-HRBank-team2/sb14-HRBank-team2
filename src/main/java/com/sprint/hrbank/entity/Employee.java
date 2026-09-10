package com.sprint.hrbank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "employee")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
    @Column(name = "profile_image_id")
    private Long profileImageId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(
        name = "employee_number",
        nullable = false,
        updatable = false)
    private String employeeNumber;

    @Column(nullable = false)
    private String position;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmployeeStatus status;

    private Employee(Department department, Long profileImageId, String name, String email,
        String employeeNumber, String position, LocalDate hireDate, EmployeeStatus status) {
        this.department = department;
        this.profileImageId = profileImageId;
        this.name = name;
        this.email = email;
        this.employeeNumber = employeeNumber;
        this.position = position;
        this.hireDate = hireDate;
        this.status = status;
    }

    public static Employee create(Department department, Long profileImageId, String name,
        String email,
        String employeeNumber, String position, LocalDate hireDate, EmployeeStatus status) {
        return new Employee(
            department, profileImageId, name, email, employeeNumber, position, hireDate, status);
    }
}
