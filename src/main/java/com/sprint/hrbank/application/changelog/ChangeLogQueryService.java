package com.sprint.hrbank.application.changelog;

import com.sprint.hrbank.adapter.persistence.changelog.ChangeLogSearchCond;
import com.sprint.hrbank.application.changelog.dto.ChangeLogResponseDto;
import com.sprint.hrbank.application.changelog.dto.CursorPageResponseChangeLogDto;
import com.sprint.hrbank.application.changelog.provided.query.ChangeLogPageMaker;
import com.sprint.hrbank.application.changelog.required.ChangeLogRepository;
import com.sprint.hrbank.common.exception.CustomRuntimeException;
import com.sprint.hrbank.common.exception.ExceptionType;
import com.sprint.hrbank.domain.chagelog.ChangeLog;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeLogQueryService implements ChangeLogPageMaker {

  private final ChangeLogRepository changeLogRepository;

  @Transactional(readOnly = true)
  public List<ChangeLogResponseDto> getChangeLog(ChangeLogSearchCond cond) {
    return changeLogRepository.search(cond).stream().map(ChangeLogResponseDto::from).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public CursorPageResponseChangeLogDto getChangeLogPage(ChangeLogSearchCond cond) {
    List<ChangeLog> searched = changeLogRepository.search(cond);
    int size = cond.size();
    boolean hasNext = searched.size() > size;
    List<ChangeLog> paged = hasNext ? searched.subList(0, size) : searched;
    List<ChangeLogResponseDto> content = paged.stream().map(ChangeLogResponseDto::from).toList();

    String nextCursor = null;
    Long nextIdAfter = null;
    if (hasNext && !paged.isEmpty()) {
      ChangeLog lastChangeLog = paged.get(paged.size() - 1);
      nextCursor = getCursor(lastChangeLog, cond.sortField());
      nextIdAfter = lastChangeLog.getId();
    }

    return CursorPageResponseChangeLogDto.builder()
        .content(content)
        .nextCursor(nextCursor)
        .nextIdAfter(nextIdAfter)
        .size(size)
        .totalElements(changeLogRepository.countByCondition(cond))
        .hasNext(hasNext)
        .build();
  }

  private String getCursor(ChangeLog changeLog, String sortField) {
    if ("ipAddress".equals(sortField)) {
      return changeLog.getIpAddress();
    }
    if ("at".equals(sortField)) {
      return changeLog.getAt().toString();
    }
    throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST);
  }
}
