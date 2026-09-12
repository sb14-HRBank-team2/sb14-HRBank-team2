package com.sprint.hrbank.application.changelog;

import com.sprint.hrbank.application.changelog.dto.ChangeLogCreateRequestDto;
import com.sprint.hrbank.application.changelog.dto.ChangeLogResponseDto;
import com.sprint.hrbank.application.changelog.dto.DiffCreateRequestDto;
import com.sprint.hrbank.application.changelog.required.ChangeLogRepository;
import com.sprint.hrbank.application.changelog.required.DiffRepository;
import com.sprint.hrbank.domain.chagelog.ChangeLog;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeLogService {

  private final ChangeLogRepository changeLogRepository;
  private final DiffRepository diffRepository;
  private final DiffService diffService;

  @Transactional
  public ChangeLogResponseDto create(ChangeLogCreateRequestDto dto, String ipAddress) {
    ChangeLog changeLog = ChangeLog.create(dto.type(), dto.employeeNumber(), dto.memo(), ipAddress);
    ChangeLog result = changeLogRepository.save(changeLog);

    // 변경내역 저장로직
    for (DiffCreateRequestDto target : dto.diffs()) {
      diffService.create(target, result.getId()); // db 2번접근
      // diffRepository.save(target.toEntity(result)); //db 1번접근
    }
    return ChangeLogResponseDto.from(result);
  }
}
