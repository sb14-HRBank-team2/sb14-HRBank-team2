package com.sprint.hrbank.adapter.webapi.changelog;

import com.sprint.hrbank.adapter.persistence.changelog.ChangeLogSearchCond;
import com.sprint.hrbank.application.changelog.ChangeLogQueryService;
import com.sprint.hrbank.application.changelog.dto.ChangeLogDetailDto;
import com.sprint.hrbank.application.changelog.dto.CursorPageResponseChangeLogDto;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/change-logs")
@RequiredArgsConstructor
public class ChangeLogController {

  private final ChangeLogQueryService changeLogQueryService;

  @GetMapping
  public CursorPageResponseChangeLogDto getChangeLogs(@ModelAttribute ChangeLogSearchCond cond) {
    return changeLogQueryService.getChangeLogPage(cond);
  }

  @GetMapping("/count")
  public Long getChangeLogCount(
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime fromDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime toDate) {
    return changeLogQueryService.getChangeLogCount(fromDate, toDate);
  }

  @GetMapping("/{id}")
  public ChangeLogDetailDto getChangeLogDetail(@PathVariable Long id) {
    return changeLogQueryService.getChangeLogDetail(id);
  }
}
