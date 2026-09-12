package com.sprint.hrbank.service;

import com.sprint.hrbank.dto.ChangeLogCreateRequestDto;
import com.sprint.hrbank.dto.ChangeLogResponseDto;
import com.sprint.hrbank.dto.DiffCreateRequestDto;
import com.sprint.hrbank.entity.ChangeLog;
import com.sprint.hrbank.repository.ChangeLogRepository;
import com.sprint.hrbank.repository.DiffRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeLogService implements ChangeLogCreator {

  private final ChangeLogRepository changeLogRepository;
  private final DiffRepository diffRepository;
  private final DiffService diffService;

  @Transactional
  public ChangeLogResponseDto create(ChangeLogCreateRequestDto dto, String ipAddress) {
    ChangeLog changeLog = dto.toEntity(ipAddress);
    ChangeLog result = changeLogRepository.save(changeLog);

    // 변경내역 저장로직
    for (DiffCreateRequestDto target : dto.getDiffs()) {
      diffService.create(target, result.getId()); // db 2번접근
      //          diffRepository.save(target.toEntity(result)); //db 1번접근
    }
    return ChangeLogResponseDto.from(result);
  }
}
