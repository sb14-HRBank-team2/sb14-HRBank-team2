package com.sprint.hrbank.application.changelog;

import com.sprint.hrbank.adapter.persistence.changelog.ChangeLogSearchCond;
import com.sprint.hrbank.application.changelog.dto.ChangeLogDetailDto;
import com.sprint.hrbank.application.changelog.dto.ChangeLogDto;
import com.sprint.hrbank.application.changelog.dto.CursorPageResponseChangeLogDto;
import com.sprint.hrbank.application.changelog.provided.query.ChangeLogPageMaker;
import com.sprint.hrbank.application.changelog.required.ChangeLogRepository;
import com.sprint.hrbank.application.employee.required.EmployeeRepository;
import com.sprint.hrbank.common.exception.CustomRuntimeException;
import com.sprint.hrbank.common.exception.ExceptionType;
import com.sprint.hrbank.domain.chagelog.ChangeLog;
import com.sprint.hrbank.domain.employee.Employee;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeLogQueryService implements ChangeLogPageMaker {

  private final ChangeLogRepository changeLogRepository;
  private final EmployeeRepository employeeRepository;
  private final DiffService diffService;

  @Transactional(readOnly = true)
  public List<ChangeLogDto> getChangeLog(ChangeLogSearchCond cond) {
    return changeLogRepository.search(cond).stream().map(ChangeLogDto::from).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public CursorPageResponseChangeLogDto getChangeLogPage(ChangeLogSearchCond cond) {
    List<ChangeLog> searched = changeLogRepository.search(cond);
    int size = cond.size();
    boolean hasNext = searched.size() > size;
    List<ChangeLog> paged = hasNext ? searched.subList(0, size) : searched;
    List<ChangeLogDto> content = paged.stream().map(ChangeLogDto::from).toList();

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

  @Transactional(readOnly = true)
  public Long getChangeLogCount(LocalDateTime fromDate, LocalDateTime toDate) {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime from = fromDate == null ? now.minusDays(7) : fromDate;
    LocalDateTime to = toDate == null ? now : toDate;

    if (from.isAfter(to)) {
      throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST);
    }

    return changeLogRepository.countByAtBetween(from, to);
  }

  @Transactional(readOnly = true)
  public ChangeLogDetailDto getChangeLogDetail(Long changeLogId) {
    ChangeLog changeLog =
        changeLogRepository
            .findById(changeLogId)
            .orElseThrow(
                () -> new CustomRuntimeException(ExceptionType.CHANGE_LOG_NOT_FOUND, changeLogId));
    Employee employee =
        employeeRepository.findByEmployeeNumber(changeLog.getEmployeeNumber()).orElse(null);

    return ChangeLogDetailDto.from(changeLog, employee, diffService.readAll(changeLogId));
  }
}
