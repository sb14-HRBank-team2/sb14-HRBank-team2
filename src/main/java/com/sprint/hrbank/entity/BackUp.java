package com.sprint.hrbank.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BackUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String worker;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    @Column(nullable = false)
    private LocalDateTime endedAt;

    @Enumerated(EnumType.STRING) // Enum을 DB에 문자열로 안전하게 저장
    @Column(nullable = false)
    private BackUpStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = true)
    private File file;

    private BackUp(String worker, LocalDateTime startedAt, LocalDateTime endedAt, BackUpStatus status, File file) {
        this.worker = worker;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.status = status;
        this.file = file;
    }

    public static BackUp of(
            String worker,
            LocalDateTime startedAt,
            LocalDateTime endedAt,
            BackUpStatus status,
            File file) {
        return new BackUp(
                worker,
                startedAt,
                endedAt,
                status,
                file
        );
    }
}