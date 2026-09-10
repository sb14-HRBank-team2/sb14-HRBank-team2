package com.sprint.hrbank.entity;

import com.sprint.hrbank.ChangeType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Enumerated(EnumType.STRING)
    ChangeType type;

    String employeeNumber;
    String memo;
    String ipAddress;
    LocalDateTime at;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

    public ChangeLog() {}

    public ChangeLog(
            ChangeType type,
            String employeeNumber,
            String memo,
            String ipAddress,
            Employee employee) {
        this.type = type;
        this.employeeNumber = employeeNumber;
        this.memo = memo;
        this.ipAddress = ipAddress;
        this.employee = employee;
        this.at = LocalDateTime.now();
    }
}
