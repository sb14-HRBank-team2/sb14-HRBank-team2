package com.sprint.hrbank.application.changelog;

import com.sprint.hrbank.adapter.persistence.changelog.ChangeLogSearchCond;
import com.sprint.hrbank.application.changelog.dto.ChangeLogResponseDto;
import com.sprint.hrbank.application.changelog.required.ChangeLogRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeLogQueryService {

  private final ChangeLogRepository changeLogRepository;

  @Transactional(readOnly = true)
  public List<ChangeLogResponseDto> getChangeLog(ChangeLogSearchCond cond) {
    return changeLogRepository.search(cond).stream().map(ChangeLogResponseDto::from).toList();
  }
}
