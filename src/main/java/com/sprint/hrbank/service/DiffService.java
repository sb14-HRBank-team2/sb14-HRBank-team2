package com.sprint.hrbank.service;

import com.sprint.hrbank.dto.DiffCreateRequestDto;
import com.sprint.hrbank.dto.DiffResponseDto;
import com.sprint.hrbank.entity.ChangeLog;
import com.sprint.hrbank.entity.Diff;
import com.sprint.hrbank.exception.CustomRuntimeException;
import com.sprint.hrbank.exception.ExceptionType;
import com.sprint.hrbank.repository.ChangeLogRepository;
import com.sprint.hrbank.repository.DiffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiffService {

  private final DiffRepository diffRepository;
  private final ChangeLogRepository changeLogRepository;

  public DiffResponseDto create(DiffCreateRequestDto dto, Integer changeLogId) {
    ChangeLog changeLog =
        changeLogRepository
            .findById(changeLogId)
            .orElseThrow(
                () -> new CustomRuntimeException(ExceptionType.CHANGE_LOG_NOT_FOUND, changeLogId));

    Diff target = dto.toEntity(changeLog);
    return DiffResponseDto.from(diffRepository.save(target));
  }
}
