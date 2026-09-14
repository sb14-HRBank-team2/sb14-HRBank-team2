package com.sprint.hrbank.dto;

import com.sprint.hrbank.entity.BackUp;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class BackUpDTO {

    private final Long id;
    private final String worker;
    private final String startedAt;
    private final String endedAt;
    private final String status;
    private final Long fileId; // 다운로드 버튼 활성화 여부 판단용 (null 가능)

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");

    public BackUpDTO(BackUp backUp) {
        this.id = backUp.getId();
        this.worker = backUp.getWorker();
        this.startedAt = backUp.getStartedAt().format(formatter);
        this.endedAt = backUp.getEndedAt().format(formatter);
        this.status = backUp.getStatus().getDescription();
        this.fileId = backUp.getFile() != null ? backUp.getFile().getId() : null;
    }

    public static BackUpDTO from(BackUp backUp) {

    }
}
