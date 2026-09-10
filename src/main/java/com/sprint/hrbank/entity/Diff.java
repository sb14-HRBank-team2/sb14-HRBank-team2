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

    @Column(nullable = false)
    String propertyName;

    String before;

    @Column(nullable = false)
    String after;

    @ManyToOne
    @JoinColumn(name = "change_log_id", nullable = false)
    ChangeLog changeLog;

    public Diff() {}

    private Diff(String propertyName, String before, String after, ChangeLog changeLog) {
        this.propertyName = propertyName;
        this.before = before;
        this.after = after;
        this.changeLog = changeLog;
    }

    public static Diff create(
            String propertyName, String before, String after, ChangeLog changeLog) {
        return new Diff(propertyName, before, after, changeLog);
    }
}
