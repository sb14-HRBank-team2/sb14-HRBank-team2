package com.sprint.hrbank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "department",
        uniqueConstraints = {@UniqueConstraint(name = "uk_department_name", columnNames = "name")})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String description;

    @Column(name = "established_date", nullable = false)
    LocalDate establishedDate;

    private Department(String name, String description) {
        this.name = name;
        this.description = description;
        this.establishedDate = LocalDate.now();
    }

    public static Department create(String name, String description) {
        return new Department(name, description);
    }
}
