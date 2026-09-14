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
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Table(
    name = "employee",
    uniqueConstraints = {
      @UniqueConstraint(name = "uk_employee_email", columnNames = "email"),
      @UniqueConstraint(name = "uk_employee_number", columnNames = "employee_number")
    })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "department_id", nullable = false)
  Department department;

  @Column(name = "profile_image_id")
  Integer profileImageId;

  @Column(nullable = false, length = 50)
  String name;

  @Column(nullable = false)
  String email;

  @Column(name = "employee_number", nullable = false, updatable = false)
  String employeeNumber;

  @Column(nullable = false)
  String position;

  @Column(name = "hire_date", nullable = false)
  LocalDate hireDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  EmployeeStatus status;

  private Employee(
      Department department,
      Integer profileImageId,
      String name,
      String email,
      String position,
      LocalDate hireDate) {
    this.department = department;
    this.profileImageId = profileImageId;
    this.name = name;
    this.email = email;
    this.employeeNumber = generateEmployeeNumber();
    this.position = position;
    this.hireDate = hireDate;
    this.status = EmployeeStatus.ACTIVE;
  }

  public static Employee create(
      Department department,
      Integer profileImageId,
      String name,
      String email,
      String position,
      LocalDate hireDate) {
    return new Employee(department, profileImageId, name, email, position, hireDate);
  }

  private String generateEmployeeNumber() {
    Long generate = Instant.now().getEpochSecond();
    return "EMP-" + generate;
  }

  public Employee update(
      String name,
      String email,
      Department department,
      String position,
      LocalDate hireDate,
      EmployeeStatus status) {
    if (name != null) {
      this.name = name;
    }
    if (email != null) {
      this.email = email;
    }
    if (department != null) {
      this.department = department;
    }
    if (position != null) {
      this.position = position;
    }
    if (hireDate != null) {
      this.hireDate = hireDate;
    }
    if (status != null) {
      this.status = status;
    }
    return this;
  }
}
