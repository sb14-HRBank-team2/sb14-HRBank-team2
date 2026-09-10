package com.sprint.hrbank.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Diff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String propertyName;
    String before;
    String after;

    @ManyToOne
    @JoinColumn(name = "change_log_id")
    ChangeLog changeLog;

    public Diff() {}

    public Diff(String propertyName, String before, String after, ChangeLog changeLog) {
        this.propertyName = propertyName;
        this.before = before;
        this.after = after;
        this.changeLog = changeLog;
    }
}
