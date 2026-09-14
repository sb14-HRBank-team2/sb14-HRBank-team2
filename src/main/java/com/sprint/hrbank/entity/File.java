package com.sprint.hrbank.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Integer id;         // 파일 id

    @Column(nullable = false)
    private String fileName;    // 파일 이름

    @Column(nullable = false)
    private String fileFormat;  // 파일 형식

    @Column(nullable = false)
    private Long size;          // 파일 크기

    @Column(name = "field", nullable = false)
    private String field;       // 파일 주소

    private File(String fileName, String fileFormat, Long size, String field) {
        this.fileName = fileName;
        this.fileFormat = fileFormat;
        this.size = size;
        this.field = field;
    }

    public static File create(String fileName, String fileFormat, Long size, String field) {
        return new File(
                fileName,
                fileFormat,
                size,
                field
        );
    }
}


